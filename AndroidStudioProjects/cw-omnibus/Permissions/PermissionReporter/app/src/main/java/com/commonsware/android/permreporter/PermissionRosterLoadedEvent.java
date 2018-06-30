/***
 Copyright (c) 2015 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.commonsware.android.permreporter;

import android.content.pm.PermissionInfo;
import java.util.ArrayList;
import java.util.HashMap;

public class PermissionRosterLoadedEvent {
  HashMap<PermissionType, ArrayList<PermissionInfo>> roster=
      new HashMap<PermissionType, ArrayList<PermissionInfo>>();

  void add(PermissionType type, PermissionInfo info) {
    ArrayList<PermissionInfo> list=roster.get(type);

    if (list==null) {
      list=new ArrayList<PermissionInfo>();
      roster.put(type, list);
    }

    list.add(info);
  }

  ArrayList<PermissionInfo> getListForType(PermissionType type) {
    return(roster.get(type));
  }
}
