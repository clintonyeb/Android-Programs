package com.demo.clinton.drsnappy.database;

import android.net.Uri;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

@ContentProvider(authority = AppContentProvider.AUTHORITY,
        database = AppDatabase.class,
        baseContentUri = AppContentProvider.BASE_CONTENT_URI)
public class AppContentProvider {
    public static final String AUTHORITY = "com.demo.clinton.drsnappy.provider";

    public static final String BASE_CONTENT_URI = "content://";

    @TableEndpoint(name = Snapshot.SNAPSHOT_END_POINT, contentProvider = AppDatabase.class)
    public static class Snapshot {

        public static final String SNAPSHOT_END_POINT = "Snapshot";

        private static Uri buildUri(String... paths) {
            Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
            for (String path : paths) {
                builder.appendPath(path);
            }
            return builder.build();
        }

        @ContentUri(path = Snapshot.SNAPSHOT_END_POINT,
                type = ContentUri.ContentType.VND_MULTIPLE + SNAPSHOT_END_POINT)
        public static final Uri SNAP_SHOT_URI = buildUri(SNAPSHOT_END_POINT);
    }
}
