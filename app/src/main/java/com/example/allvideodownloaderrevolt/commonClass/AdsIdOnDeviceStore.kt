package com.example.allvideodownloaderrevolt.commonClass

import android.app.Activity
import android.util.Log
import com.androidads.adsdemo.model.SplashCommonModel
import com.androidnetworking.BuildConfig
import com.google.gson.Gson

object AdsIdOnDeviceStore {
    fun GetInterstitial(activity: Activity): SplashCommonModel {
        return if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.i
            ))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.i)) {
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.i.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "2"
                    saveobj.id = SaveRemoteGet
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.i
            ))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.i)) {
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.i.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "1"
                    saveobj.id = SaveRemoteGet
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G,F",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.i
            )) || (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.i
            )))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                try {
                    if (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.g?.i
                        )
                    ) {
                        isFacebook = false
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.i.toString()
                    } else if (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.f?.i
                        )
                    ) {
                        isFacebook = true
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.i.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.id = SaveRemoteGet
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()

                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F,G",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.i
            )) || (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.i
            )))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                try {
                    if (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.f?.i
                        )
                    ) {
                        isFacebook = true
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.i.toString()
                    } else if (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.g?.i
                        )
                    ) {
                        isFacebook = false
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.i.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.id = SaveRemoteGet
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                settestingads.accountNo = ""
                settestingads
            }
        } else if (!Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) && SharedPreferences.getStringName(
                Constant.is_advertise_available
            ).equals("2", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            settestingads.accountType = "3"
            settestingads.id = "qureka"
            settestingads.accountNo = ""
            settestingads
        } else if (SharedPreferences.advertiseModel == null || Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) || SharedPreferences.advertiseModel?.priority.equals("NA", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        } else {
            val settestingads = SplashCommonModel()
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        }
    }

    fun GetInterstitialSplash(): SplashCommonModel {
        return if (!BuildConfig.DEBUG) {
            val gson = Gson()
            var SaveRemoteGet = ""
            try {
                if (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.i
                    )
                ) {
                    SaveRemoteGet = SharedPreferences.advertiseModel?.f?.i.toString()
                }
            } catch (e: Exception) {
            }
            if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                val saveobj = SplashCommonModel()
                saveobj.setAccountType("1")
                saveobj.setId(SaveRemoteGet)
                saveobj.setAccountNo("")
                Log.e("GetInt", "Int AccountType  " + saveobj.getAccountType())
                Log.e("GetInt", "Int AdsId  " + saveobj.getId())
                Log.e("GetInt", "Int AccountNo  " + saveobj.getAccountNo())
                saveobj
            } else {
                val settestingads = SplashCommonModel()
                settestingads.setAccountType("0") //TODO: default 0
                settestingads.setId("ca-app-pub-3940256099942544/1033173712")
                settestingads.setAccountNo("")
                settestingads
            }
        } else {
            val settestingads = SplashCommonModel()
            settestingads.setAccountType("0")
            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
            //                settestingads.setAccountType("2");
            //                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
            settestingads.setAccountNo("")
            settestingads
        }
    }

    fun GetNativeAds(AdSize: Int, activity: Activity, pos: Int): SplashCommonModel {
        Log.d("23/01", "" + (SharedPreferences.advertiseModel == null))
        return if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb)))
        ) {
            Log.d("23/01", "1")
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (AdSize == 2) {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n.toString()
                        }
                    } else {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb.toString()
                        }
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "2"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/2247696110"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb)))
        ) {
            Log.d("23/01", "2")
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (AdSize == 2) {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n.toString()
                        }
                    } else {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb.toString()
                        }
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "1"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G,F",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.g != null && (

                    !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n) || !Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.nb
                    ))) || (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb))))
        ) {
            Log.d("23/01", "3")
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                if (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.n
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb))
                ) {
                    isFacebook = false
                    //                    SaveRemoteGet = SharedPreferences.advertiseModel!!.getG().getN();
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                } else if (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.n
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb))
                ) {
                    isFacebook = true
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/2247696110"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F,G",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb))) || (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb))))
        ) {
            Log.d("23/01", "4")
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                if (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.n
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb))
                ) {
                    isFacebook = true
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                } else if (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.n
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb))
                ) {
                    isFacebook = false
                    //                    SaveRemoteGet = SharedPreferences.advertiseModel!!.getG().getN();
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if (!Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) && SharedPreferences.getStringName(
                Constant.is_advertise_available
            ).equals("2", ignoreCase = true)
        ) {
            Log.d("23/01", "5")
            val settestingads = SplashCommonModel()
            settestingads.accountType = "3"
            settestingads.id = "qureka"
            settestingads.accountNo = ""
            settestingads
        } else if (SharedPreferences.advertiseModel == null || Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) || SharedPreferences.advertiseModel?.priority.equals("NA", ignoreCase = true)
        ) {
            Log.d("23/01", "6")
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//            Logger.d("16/05", "Model:-" + new Gson().toJson(SharedPreferences.advertiseModel!!));
//            Logger.d("16/05", "Empty:-" + Utils.isValidationEmpty(SharedPreferences.advertiseModel!!.getPriority()));
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        } else {
            Log.d("23/01", "7")
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/2247696110"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        }
    }

    fun GetNativeAds(
        AdSize: Int, activity: Activity, pos: Int, isExtra: Boolean
    ): SplashCommonModel {
        return if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.n
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb)))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (AdSize == 2) {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n2.toString()
                        }
                    } else {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb2.toString()
                        }
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "2"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/2247696110"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n2
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2)))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (AdSize == 2) {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n2)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n2.toString()
                        }
                    } else {
                        if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2)) {
                            SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb2.toString()
                        }
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "1"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                    "1",
                    ignoreCase = true
                ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G,F",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.g != null && (

                    !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n2) || !Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.nb2
                    ))) || (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n2
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2))))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                if (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.n2
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2))
                ) {
                    isFacebook = false
                    //                    SaveRemoteGet = SharedPreferences.advertiseModel?.getG().getN();
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n2.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb2.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                } else if (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.n2
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2))
                ) {
                    isFacebook = true
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n2.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb2.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/2247696110"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F,G",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.n2
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2))) || (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.n2
            ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2))))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                if (SharedPreferences.advertiseModel?.f != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.n2
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2))
                ) {
                    isFacebook = true
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.n2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.n2.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.nb2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.f?.nb2.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                } else if (SharedPreferences.advertiseModel?.g != null && (!Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.g?.n2
                    ) || !Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2))
                ) {
                    isFacebook = false
                    //                    SaveRemoteGet = SharedPreferences.advertiseModel?.getG().getN();
                    SaveRemoteGet = ""
                    try {
                        if (AdSize == 2) {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.n2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.n2.toString()
                            }
                        } else {
                            if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.nb2)) {
                                SaveRemoteGet = SharedPreferences.advertiseModel?.g?.nb2.toString()
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if (!Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) && SharedPreferences.getStringName(
                Constant.is_advertise_available
            ).equals("2", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            settestingads.accountType = "3"
            settestingads.id = "qureka"
            settestingads.accountNo = ""
            settestingads
        } else if (SharedPreferences.advertiseModel == null || Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) || SharedPreferences.advertiseModel?.priority.equals("NA", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//            Logger.d("16/05", "Model:-" + new Gson().toJson(SharedPreferences.advertiseModel?));
//            Logger.d("16/05", "Empty:-" + Utils.isValidationEmpty(SharedPreferences.advertiseModel?.getPriority()));
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        } else {
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/2247696110"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        }
    }

    fun GetSmallNativeBannerAds(activity: Activity): SplashCommonModel {
        return if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.b
            ))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.g?.b)) {
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.b.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "2"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("0");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/6300978111"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F",
                ignoreCase = true
            ) && (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.snb
            ))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                try {
                    if (!Utils.isValidaEmptyWithZero(SharedPreferences.advertiseModel?.f?.snb)) {
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.snb.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.accountType = "1"
                    saveobj.id = SaveRemoteGet
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "G,F",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.b
            )) || (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.snb
            )))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                try {
                    if (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.g?.b
                        )
                    ) {
                        isFacebook = false
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.b.toString()
                    } else if (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.f?.snb
                        )
                    ) {
                        isFacebook = true
                        SaveRemoteGet = ""
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.snb.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                //                settestingads.setAccountType("1");
//                settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                settestingads.accountType = "0"
                settestingads.id = "ca-app-pub-3940256099942544/6300978111"
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if ((Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) || (!Utils.isValidationEmpty(
                SharedPreferences.getStringName(Constant.is_advertise_available)!!
            ) && SharedPreferences.getStringName(Constant.is_advertise_available).equals(
                "1", ignoreCase = true
            ))) && SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) && SharedPreferences.advertiseModel?.priority.equals(
                "F,G",
                ignoreCase = true
            ) && ((SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.f?.snb
            )) || (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                SharedPreferences.advertiseModel?.g?.b
            )))
        ) {
            if (!BuildConfig.DEBUG) {
                val gson = Gson()
                var SaveRemoteGet = ""
                var isFacebook = false
                try {
                    if (SharedPreferences.advertiseModel?.f != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.f?.snb
                        )
                    ) {
                        isFacebook = true
                        SaveRemoteGet = ""
                        SaveRemoteGet = SharedPreferences.advertiseModel?.f?.snb.toString()
                    } else if (SharedPreferences.advertiseModel?.g != null && !Utils.isValidaEmptyWithZero(
                            SharedPreferences.advertiseModel?.g?.b
                        )
                    ) {
                        isFacebook = false
                        SaveRemoteGet = SharedPreferences.advertiseModel?.g?.b.toString()
                    }
                } catch (e: Exception) {
                }
                if (!SaveRemoteGet.equals("", ignoreCase = true)) {
                    val saveobj = SplashCommonModel()
                    saveobj.id = SaveRemoteGet
                    if (isFacebook) {
                        saveobj.accountType = "1"
                    } else {
                        saveobj.accountType = "2"
                    }
                    saveobj.addSize = "1"
                    saveobj.accountNo = ""
                    Log.e("GetInt", "Int AccountType  " + saveobj.accountType)
                    Log.e("GetInt", "Int AdsId  " + saveobj.id)
                    Log.e("GetInt", "Int AccountNo  " + saveobj.accountNo)
                    saveobj
                } else {
                    val settestingads = SplashCommonModel()
                    settestingads.accountType = "0" //TODO: default 0
                    settestingads.id = "ca-app-pub-3940256099942544/1033173712"
                    settestingads.addSize = "1"
                    settestingads.accountNo = ""
                    settestingads
                }
            } else {
                val settestingads = SplashCommonModel()
                settestingads.accountType = "0"
                settestingads.id = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
                //                settestingads.setAccountType("2");
//                settestingads.setId("ca-app-pub-3940256099942544/1033173712");
                settestingads.addSize = "1"
                settestingads.accountNo = ""
                settestingads
            }
        } else if (!Utils.isValidationEmpty(SharedPreferences.getStringName(Constant.is_advertise_available)!!) && SharedPreferences.getStringName(
                Constant.is_advertise_available
            ).equals("2", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            settestingads.accountType = "3"
            settestingads.id = "qureka"
            settestingads.accountNo = ""
            settestingads
        } else if (SharedPreferences.advertiseModel == null || Utils.isValidationEmpty(
                SharedPreferences.advertiseModel?.priority!!
            ) || SharedPreferences.advertiseModel?.priority.equals("NA", ignoreCase = true)
        ) {
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        } else {
            val settestingads = SplashCommonModel()
            //            settestingads.setAccountType("1");
//            settestingads.setId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
            settestingads.accountType = "0"
            settestingads.id = "ca-app-pub-3940256099942544/6300978111"
            settestingads.addSize = "1"
            settestingads.accountNo = ""
            settestingads
        }
    }
}