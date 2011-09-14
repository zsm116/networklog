package com.googlecode.iptableslog;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

public class ApplicationsTracker {
  public static ArrayList<AppEntry> installedApps = new ArrayList<AppEntry>();
  public static Hashtable<String, AppEntry> installedAppsHash = new Hashtable<String, AppEntry>();

  public static class AppEntry {
    String name;
    Drawable icon;
    int uid;
  }

  public static void getInstalledApps(Context context) {
    installedApps.clear();

    List<ApplicationInfo> apps = new ArrayList<ApplicationInfo>();
    PackageManager pm = context.getPackageManager();

    apps = pm.getInstalledApplications(0);

    for(ApplicationInfo app : apps) {
      String name = app.loadLabel(pm).toString();
      Drawable icon = app.loadIcon(pm);
      int uid = app.uid;
      String sUid = Integer.toString(uid);

      AppEntry entryHash = installedAppsHash.get(sUid);

      AppEntry entry = new AppEntry();
      entry.name = name;
      entry.icon = icon;
      entry.uid = uid;

      installedApps.add(entry);

      if(entryHash != null) {
        entryHash.name.concat("; " + name);
      } else {
        installedAppsHash.put(sUid, entry);
      } 
    }
  }
}