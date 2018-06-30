// Copyright Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, GGLInstanceIDDelegate {

  var window: UIWindow?

  var connectedToGCM = false
  var senderId: String?

  let apnsRegisteredKey = "onApnsRegistered"
  let registrationKey = "onRegistrationCompleted"
  let tokenRefreshKey = "onTokenRefresh"
  let messageKey = "onMessageReceived"

  func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions:
    [NSObject: AnyObject]?) -> Bool {
      // Configure the Google context: parses the GoogleService-Info.plist, and initializes
      // the services that have entries in the file
      var configureError:NSError?
      GGLContext.sharedInstance().configureWithError(&configureError)
      assert(configureError == nil, "Error configuring Google services: \(configureError)")
      senderId = GGLContext.sharedInstance().configuration.gcmSenderID

      // Register for remote notifications
      let settings: UIUserNotificationSettings =
      UIUserNotificationSettings(forTypes: [.Alert, .Badge, .Sound], categories: nil)
      application.registerUserNotificationSettings(settings)
      application.registerForRemoteNotifications()

      let gcmConfig = GCMConfig.defaultConfig()
      gcmConfig.logLevel = GCMLogLevel.Debug
      GCMService.sharedInstance().startWithConfig(gcmConfig)

      return true
  }

  func applicationDidBecomeActive( application: UIApplication) {
    // Connect to the GCM server to receive non-APNS notifications
    GCMService.sharedInstance().connectWithHandler({
      (NSError error) -> Void in
      if error != nil {
        print("Could not connect to GCM: \(error.localizedDescription)")
      } else {
        self.connectedToGCM = true
        print("Connected to GCM")
      }
    })
  }

  func applicationDidEnterBackground(application: UIApplication) {
    GCMService.sharedInstance().disconnect()
    self.connectedToGCM = false
  }

  func application( application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken
    deviceToken: NSData ) {
      NSNotificationCenter.defaultCenter().postNotificationName(
        apnsRegisteredKey, object: nil, userInfo: ["deviceToken" : deviceToken])
  }

  func application( application: UIApplication, didFailToRegisterForRemoteNotificationsWithError
    error: NSError ) {
      print("Registration for remote notification failed with error: \(error.localizedDescription)")
      let userInfo = ["error": error.localizedDescription]
      NSNotificationCenter.defaultCenter().postNotificationName(
        registrationKey, object: nil, userInfo: userInfo)
  }

  func application( application: UIApplication,
    didReceiveRemoteNotification userInfo: [NSObject : AnyObject]) {
      print("Notification received: \(userInfo)")
      // This works only if the app started the GCM service
      GCMService.sharedInstance().appDidReceiveMessage(userInfo);
      // Handle the received message
      NSNotificationCenter.defaultCenter().postNotificationName(messageKey, object: nil,
        userInfo: userInfo)
  }

  func application( application: UIApplication,
    didReceiveRemoteNotification userInfo: [NSObject : AnyObject],
    fetchCompletionHandler handler: (UIBackgroundFetchResult) -> Void) {
      print("Notification received: \(userInfo)")
      // This works only if the app started the GCM service
      GCMService.sharedInstance().appDidReceiveMessage(userInfo);
      // Handle the received message
      // Invoke the completion handler passing the appropriate UIBackgroundFetchResult value
      NSNotificationCenter.defaultCenter().postNotificationName(messageKey, object: nil,
        userInfo: userInfo)
      handler(UIBackgroundFetchResult.NoData);
  }

  func onTokenRefresh() {
    // A rotation of the registration tokens is happening, so the app needs to request a new token.
    print("The GCM registration token needs to be changed.")
    NSNotificationCenter.defaultCenter().postNotificationName(tokenRefreshKey, object: nil)
  }

}