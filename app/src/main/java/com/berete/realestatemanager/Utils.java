package com.berete.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.berete.realestatemanager.domain.models.Property;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Created by Philippe on 21/02/2018. */
public class Utils {

  /**
   * Conversion d'un prix d'un bien immobilier (Dollars vers Euros) NOTE : NE PAS SUPPRIMER, A
   * MONTRER DURANT LA SOUTENANCE
   *
   * @param dollars
   * @return
   */
  public static int convertDollarToEuro(int dollars) {
    return (int) Math.round(dollars * 0.812);
  }

  /**
   * Conversion de la date d'aujourd'hui en un format plus approprié NOTE : NE PAS SUPPRIMER, A
   * MONTRER DURANT LA SOUTENANCE
   *
   * @return
   */
  public static String getTodayDate() {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    return dateFormat.format(new Date());
  }

  /**
   * Vérification de la connexion réseau NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
   *
   * @param context
   * @return
   */
  public static Boolean isInternetAvailable(Context context) {
    WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    return wifi.isWifiEnabled();
  }
}
