package com.androidads.adsdemo.common;

import android.app.Activity;
import android.util.Patterns;

import com.androidads.adsdemo.R;
import com.androidads.adsdemo.model.QurekaandPreadChampAdvertiseModel;

import java.util.ArrayList;
import java.util.Random;

public class AdvertiseUtils {

    public static String PredChampURL = "";
    public static String BitcoinURL = "";
    public static String GameURL = "";

    public static Boolean isValidatEmpty(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("") || value.length() == 0
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isCheckURLValid(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("")
                || value.length() == 0
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(value).matches();
        }
    }


    public static Boolean isValidaEmptyWithZero(String value) {
        if (value == null || value.isEmpty()
                || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("")
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")
                || value.length() == 0 || value.equalsIgnoreCase("0")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isValidationEmptyWithZero(String value) {
        if (value == null || value.isEmpty()
                || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("")
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")
                || value.length() == 0 || value.equalsIgnoreCase("0")) {
            return true;
        } else {
            return false;
        }
    }


    public static String getInterstialButtonText(Activity activity) {

        ArrayList<String> interstialheaderText = new ArrayList<>();
        interstialheaderText.add(activity.getString(R.string.play_now));
        interstialheaderText.add(activity.getString(R.string.play_game));

        final int min = 0;
        final int max = interstialheaderText.size() - 1;
        final int random = new Random().nextInt((max - min) + 1) + min;

        return interstialheaderText.get(random);
    }

    public static int getPredChampOnlyGIFImage() {
        int[] gifArrayList = {
                R.drawable.ic_predchamp_gif_1,
                R.drawable.ic_predchamp_gif_2,
                R.drawable.ic_predchamp_gif_3,
                R.drawable.ic_predchamp_gif_4,
                R.drawable.ic_predchamp_gif_5,
                R.drawable.ic_predchamp_gif_6,
                R.drawable.ic_predchamp_gif_7,
        };

        final int min = 0;
        final int max = gifArrayList.length - 1;
        final int random = new Random().nextInt((max - min) + 1) + min;

        return gifArrayList[random];
    }


    public static QurekaandPreadChampAdvertiseModel getPredchampHeaderTitleDescriptionList(Activity activity) {

        ArrayList<QurekaandPreadChampAdvertiseModel> qurekaandPreadChampAdvertiseModelArrayList = new ArrayList<>();

        if (AdvertiseUtils.PredChampURL != null && !Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)) {

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//91
                    activity.getString(R.string.preadchamp_play_game),
                    "Play & Win Coins Daily",
                    "Come play Cricket Match Prediction for 50,000 Coins daily.",
                    R.drawable.ic_predchamp_inters_banner1,
                    R.drawable.ic_predchamp_gif_1,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//92
                    activity.getString(R.string.preadchamp_play_game),
                    "Sports Prediction Contest Live!",
                    "Predict & Win | No install required",
                    R.drawable.ic_predchamp_inters_banner2,
                    R.drawable.ic_predchamp_gif_2,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//93
                    activity.getString(R.string.preadchamp_play_game),
                    "Today Match Toss Win Prediction Contest",
                    "Predict and Win Now!",
                    R.drawable.ic_predchamp_inters_banner3,
                    R.drawable.ic_predchamp_gif_3,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//94
                    activity.getString(R.string.preadchamp_play_game),
                    "Pool prize is 50,000 coins",
                    "Cricket  Prediction Contest is Live! Predict Now",
                    R.drawable.ic_predchamp_inters_banner4,
                    R.drawable.ic_predchamp_gif_4,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//95
                    activity.getString(R.string.preadchamp_play_game),
                    "Prediction Time to Win Coins!",
                    "Contests Live. Play Now",
                    R.drawable.ic_predchamp_inters_banner5,
                    R.drawable.ic_predchamp_gif_5,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//96
                    activity.getString(R.string.preadchamp_play_game),
                    "Predict the match score & Win Coins now!",
                    "Predict & Win",
                    R.drawable.ic_predchamp_inters_banner6,
                    R.drawable.ic_predchamp_gif_6,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//97
                    activity.getString(R.string.preadchamp_play_game),
                    "Who will win Cricket match today?",
                    "Predict Now & Win Coins",
                    R.drawable.ic_predchamp_inters_banner7,
                    R.drawable.ic_predchamp_gif_7,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//98
                    activity.getString(R.string.preadchamp_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Prediction  & win coins daily",
                    R.drawable.ic_predchamp_small_native1,
                    R.drawable.ic_predchamp_gif_1,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//99
                    activity.getString(R.string.preadchamp_play_game),
                    "Innings for 50,000 coins",
                    "Prediction Champ Khelo aur Jeeto coins. No install required.",
                    R.drawable.ic_predchamp_small_native2,
                    R.drawable.ic_predchamp_gif_2,
                    AdvertiseUtils.PredChampURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//100
                    activity.getString(R.string.preadchamp_play_game),
                    "Best Match Prediction game Today",
                    "Predict results & Win Now!",
                    R.drawable.ic_predchamp_small_native3,
                    R.drawable.ic_predchamp_gif_3,
                    AdvertiseUtils.PredChampURL
            ));

        }

        if (AdvertiseUtils.BitcoinURL != null && !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//1
                    activity.getString(R.string.bitcoin_play_game),
                    "Aaj Math Quiz khela kya?",
                    "Play and win coins now",
                    R.drawable.ic_qureka_inters_banner1,//Bansi
                    R.drawable.ic_qureka_gif_1,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//2
                    activity.getString(R.string.bitcoin_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Quizzes  & win coins daily",
                    R.drawable.ic_qureka_inters_banner2,
                    R.drawable.ic_qureka_gif_2,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//3
                    activity.getString(R.string.bitcoin_play_game),
                    "Jeeto 10,000 Coins",
                    "Show your Math knowledge & Win Now",
                    R.drawable.ic_qureka_inters_banner3,
                    R.drawable.ic_qureka_gif_3,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//4
                    activity.getString(R.string.bitcoin_play_game),
                    "Tech quiz for 50,000 coins open",
                    "Test your tech skills & win now",
                    R.drawable.ic_qureka_inters_banner4,
                    R.drawable.ic_qureka_gif_4,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//5
                    activity.getString(R.string.bitcoin_play_game),
                    "Pool prize is 50,000 coins",
                    "Sharpen your Cricket knowledge & win now",
                    R.drawable.ic_qureka_inters_banner5,
                    R.drawable.ic_qureka_gif_5,
                    AdvertiseUtils.BitcoinURL
            ));

        }

        if (AdvertiseUtils.GameURL != null && !Utils.isValidatEmpty(AdvertiseUtils.GameURL)) {


            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//game1
                    activity.getString(R.string.play_games),
                    "Play Candy Burst To Win",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_game_small_native1,
                    R.drawable.ic_game_gif_1,
                    AdvertiseUtils.GameURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//game2
                    activity.getString(R.string.play_games),
                    "Play Bubble Shooter To Win",
                    "Pool prize is 1,00,000 coins",
                    R.drawable.ic_game_small_native2,
                    R.drawable.ic_game_gif_2,
                    AdvertiseUtils.GameURL
            ));


            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//game3
                    activity.getString(R.string.play_games),
                    "Play MGL Games",
                    "Earn Upto 1,00,000 Coins Daily",
                    R.drawable.ic_game_small_native3,
                    R.drawable.ic_game_gif_3,
                    AdvertiseUtils.GameURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//game4
                    activity.getString(R.string.play_games),
                    "Play Fruit Chop Game",
                    "No install required! Play Game Now",
                    R.drawable.ic_game_small_native4,
                    R.drawable.ic_game_gif_4,
                    AdvertiseUtils.GameURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//game5
                    activity.getString(R.string.play_games),
                    "Play Games To Win Coins",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_game_small_native5,
                    R.drawable.ic_game_gif_5,
                    AdvertiseUtils.GameURL
            ));

        }

        if (qurekaandPreadChampAdvertiseModelArrayList!=null && qurekaandPreadChampAdvertiseModelArrayList.size()>0) {

            final int min = 0;
            final int max = qurekaandPreadChampAdvertiseModelArrayList.size() - 1;
            final int random = new Random().nextInt((max - min) + 1) + min;

            return qurekaandPreadChampAdvertiseModelArrayList.get(random);

        }
        return null;
    }

    public static QurekaandPreadChampAdvertiseModel getSmartBannerModelRandom(Activity activity) {

        ArrayList<QurekaandPreadChampAdvertiseModel> qurekaandPreadChampAdvertiseModelArrayList = new ArrayList<>();

        if (AdvertiseUtils.PredChampURL != null && !Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)) {


        //Predchamp
        qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Predchamp
                activity.getString(R.string.preadchamp_play_game),
                "Play Sports Prediction Contest & Win Upto 50,000 Coins",
                "No install required! Predict Now",
                R.drawable.ic_predchamp_smart_banner1,
                R.drawable.ic_predchamp_gif_2,
                AdvertiseUtils.PredChampURL
        ));

        qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Predchamp2
                activity.getString(R.string.preadchamp_play_game),
                "Best Match Prediction game Today",
                "Predict results & Win Now!",
                R.drawable.ic_predchamp_smart_banner2,
                R.drawable.ic_predchamp_gif_4,
                AdvertiseUtils.PredChampURL
        ));

        qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Predchamp3
                activity.getString(R.string.preadchamp_play_game),
                "Play Sports Prediction Contest & Win Upto 50,000 Coins",
                "No install required! Predict Now",
                R.drawable.ic_predchamp_smart_banner3,
                R.drawable.ic_predchamp_gif_2,
                AdvertiseUtils.PredChampURL
        ));

        qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Predchamp4
                activity.getString(R.string.preadchamp_play_game),
                "Best Match Prediction game Today",
                "Predict results & Win Now!",
                R.drawable.ic_predchamp_smart_banner4,
                R.drawable.ic_predchamp_gif_4,
                AdvertiseUtils.PredChampURL
        ));

        qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Predchamp5
                activity.getString(R.string.preadchamp_play_game),
                "Best Match Prediction game Today",
                "Predict results & Win Now!",
                R.drawable.ic_predchamp_smart_banner5,
                R.drawable.ic_predchamp_gif_4,
                AdvertiseUtils.PredChampURL
        ));

        }

        if (AdvertiseUtils.BitcoinURL != null && !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {

            // Qureka
            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Qureka1
                    activity.getString(R.string.bitcoin_play_game),
                    "Aaj Math Quiz khela kya?",
                    "Play and win coins now",
                    R.drawable.ic_qureka_smart_banner1,
                    R.drawable.ic_qureka_gif_1,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Qureka2
                    activity.getString(R.string.bitcoin_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Quizzes  & win coins daily",
                    R.drawable.ic_qureka_smart_banner2,
                    R.drawable.ic_qureka_gif_2,
                    AdvertiseUtils.BitcoinURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//Qureka3
                    activity.getString(R.string.bitcoin_play_game),
                    "Pool prize is 50,000 coins",
                    "Sharpen your Cricket knowledge & win now",
                    R.drawable.ic_qureka_smart_banner3,
                    R.drawable.ic_qureka_gif_3,
                    AdvertiseUtils.BitcoinURL
            ));
        }

        if (AdvertiseUtils.GameURL != null && !Utils.isValidatEmpty(AdvertiseUtils.GameURL)) {

            // MGL
            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(// MGL1
                    activity.getString(R.string.play_games),
                    "Play MGL Games",
                    "Earn Upto 1,00,000 Coins Daily",
                    R.drawable.ic_mgl_smart_banner1,
                    R.drawable.ic_game_gif_1,
                    AdvertiseUtils.GameURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//MGL2
                    activity.getString(R.string.play_games),
                    "Play Fruit Chop Game",
                    "No install required! Play Game Now",
                    R.drawable.ic_mgl_smart_banner2,
                    R.drawable.ic_game_gif_2,
                    AdvertiseUtils.GameURL
            ));

            qurekaandPreadChampAdvertiseModelArrayList.add(new QurekaandPreadChampAdvertiseModel(//MGL3
                    activity.getString(R.string.play_games),
                    "Play Games To Win Coins",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_mgl_smart_banner3,
                    R.drawable.ic_game_gif_3,
                    AdvertiseUtils.GameURL
            ));

        }

        if (qurekaandPreadChampAdvertiseModelArrayList!=null && qurekaandPreadChampAdvertiseModelArrayList.size()>0) {

            final int min = 0;
            final int max = qurekaandPreadChampAdvertiseModelArrayList.size() - 1;
            final int random = new Random().nextInt((max - min) + 1) + min;

            return qurekaandPreadChampAdvertiseModelArrayList.get(random);

        }
        return null;
    }

    public static int getPredChampIntertialLayout() {

        int[] layoutsPredChamp = {
                R.layout.custome_predchamp_interad,
                R.layout.custome_predchamp_interad2,
                R.layout.custome_predchamp_interad3,
        };
        final int min = 0;
        final int max = layoutsPredChamp.length - 1;
        final int random = new Random().nextInt((max - min) + 1) + min;

        return layoutsPredChamp[random];
    }

}
