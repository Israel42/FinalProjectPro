package com.example.finalprojectpro;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public class ContextUtils extends ContextWrapper {

    public ContextUtils(Context base) {
        super(base);
    }
    public final ContextWrapper updateLocale(@NotNull Context c, @NotNull Locale localeToSwitchTo) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(localeToSwitchTo, "localeToSwitchTo");
        Context context = c;
        Resources var10000 = c.getResources();
        Intrinsics.checkNotNullExpressionValue(var10000, "context.resources");
        Resources resources = var10000;
        Configuration var7 = resources.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(var7, "resources.configuration");
        Configuration configuration = var7;
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = new LocaleList(new Locale[]{localeToSwitchTo});
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
        } else {
            configuration.locale = localeToSwitchTo;
        }

        if (Build.VERSION.SDK_INT >= 25) {
            Context var8 = c.createConfigurationContext(configuration);
            Intrinsics.checkNotNullExpressionValue(var8, "context.createConfigurationContext(configuration)");
            context = var8;
        } else {
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

        return (ContextWrapper)(new ContextUtils(context));
    }
}
