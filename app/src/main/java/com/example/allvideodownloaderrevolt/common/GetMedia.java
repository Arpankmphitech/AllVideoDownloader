package com.example.allvideodownloaderrevolt.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.example.allvideodownloaderrevolt.models.AudioModel;
import com.example.allvideodownloaderrevolt.models.FolderModel;
import com.example.allvideodownloaderrevolt.models.GalleyAlbumModel;
import com.example.allvideodownloaderrevolt.models.GalleyPhotosListModel;
import com.example.allvideodownloaderrevolt.models.VideoModel;
import com.google.gson.Gson;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetMedia {
    public static String mainFolder = "MxVideoPlayer";
    public static String parentFolder = ".mx_private";
    Context context;

    public GetMedia(Context context2) {
        this.context = context2;
    }

    public ArrayList<FolderModel> getfolderList() {
        Cursor query = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"bucket_id"}, (String) null, (String[]) null, (String) null);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.clear();
        while (query.moveToNext()) {
            linkedHashSet.add(query.getString(0));
        }
        ArrayList arrayList = new ArrayList(linkedHashSet);
        String[] strArr = {"bucket_display_name", "_data", "bucket_id", "date_modified", "_size"};
        ArrayList<FolderModel> arrayList2 = new ArrayList<>();
        arrayList2.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor query2 = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, strArr, "bucket_id =?", new String[]{(String) arrayList.get(i)}, "date_modified DESC");
            if (query2.moveToNext()) {
                FolderModel folderModel = new FolderModel();
                folderModel.setBucket(query2.getString(0));
                folderModel.setData(query2.getString(1));
                folderModel.setBid(query2.getString(2));
                folderModel.setVideoCount(String.valueOf(query2.getCount()));
//                folderModel.setDate(folderDate(query2.getString(3)));
//                folderModel.setSize(size(query2.getString(4)));
                Log.e("folder_path", "path: " + query2.getString(3));
                arrayList2.add(folderModel);
            }
        }
        Log.e("10/01", "getfolderList: " + new Gson().toJson(arrayList2));

        return arrayList2;
    }

    public List<VideoModel> getVideoByFolder(String str) {
        String[] strArr = {"_id", "title", "_display_name", "_data", "date_modified", TypedValues.TransitionType.S_DURATION, "resolution", "_size"};
        Cursor query = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, strArr, "bucket_id =?", new String[]{str}, "date_modified DESC");
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        while (query.moveToNext()) {
            VideoModel videoModel = new VideoModel();
            videoModel.setId(query.getString(0));
            videoModel.setTitle(query.getString(1));
            videoModel.setDisplayName(query.getString(2));
            videoModel.setData(query.getString(3));
            videoModel.setDate(videoDate(query.getString(4)));
            videoModel.setDuartion(duration(query.getString(5)));
            videoModel.setResolution(query.getString(6));
            videoModel.setSize(size(query.getString(7)));
            Log.e("folder_path", "path: " + query.getString(3));
            arrayList.add(videoModel);
        }
        return arrayList;
    }

    public List<AudioModel> getAllMusic() {
        ArrayList arrayList = new ArrayList();
        Cursor query = this.context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "album", "artist"}, (String) null, (String[]) null, (String) null);
        if (query != null) {
            while (query.moveToNext()) {
                AudioModel audioModel = new AudioModel();
                String string = query.getString(0);
                String string2 = query.getString(1);
                String string3 = query.getString(2);
                audioModel.setaName(string.substring(string.lastIndexOf("/") + 1));
                audioModel.setaAlbum(string2);
                audioModel.setaArtist(string3);
                audioModel.setaPath(string);
                arrayList.add(audioModel);
            }
            query.close();
        }
        return arrayList;
    }

    public List<GalleyAlbumModel> getImageFolder(Activity activity) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.clear();
        arrayList2.clear();
        Cursor query = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"bucket_display_name", "_data", "_id"}, (String) null, (String[]) null, (String) null);
        if (query != null && query.getCount() > 0) {
            if (query.moveToFirst()) {
                int columnIndex = query.getColumnIndex("bucket_display_name");
                int columnIndex2 = query.getColumnIndex("_data");
                int columnIndex3 = query.getColumnIndex("_id");
                do {
                    String string = query.getString(columnIndex);
                    String string2 = query.getString(columnIndex2);
                    String string3 = query.getString(columnIndex3);
                    GalleyPhotosListModel galleyPhotosListModel = new GalleyPhotosListModel();
                    galleyPhotosListModel.setAlbumName(string);
                    galleyPhotosListModel.setPhotoUri(string2);
                    galleyPhotosListModel.setId(Integer.valueOf(string3).intValue());
                    if (arrayList2.contains(string)) {
                        Iterator it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                GalleyAlbumModel galleyAlbumModel = (GalleyAlbumModel) it.next();
                                if (galleyAlbumModel.getName().equals(string)) {
                                    galleyAlbumModel.getAlbumPhotos().add(galleyPhotosListModel);
                                    Log.i("DeviceImageManager", "A photo was added to album => " + string);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        GalleyAlbumModel galleyAlbumModel2 = new GalleyAlbumModel();
                        Log.i("DeviceImageManager", "A new album was created => " + string);
                        galleyAlbumModel2.setId(galleyPhotosListModel.getId());
                        galleyAlbumModel2.setName(string);
                        galleyAlbumModel2.setCoverUri(galleyPhotosListModel.getPhotoUri());
                        galleyAlbumModel2.getAlbumPhotos().add(galleyPhotosListModel);
                        Log.i("DeviceImageManager", "A photo was added to album => " + string);
                        arrayList.add(galleyAlbumModel2);
                        arrayList2.add(string);
                    }
                } while (query.moveToNext());
            }
            query.close();
        }
        return arrayList;
    }

    public static String duration(String str) {
        try {
            long parseInt = (long) Integer.parseInt(str);
            long hours = TimeUnit.MILLISECONDS.toHours(parseInt);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(parseInt));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(parseInt));
            if (hours >= 1) {
                return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});
            }
            return String.format("%02d:%02d", new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)});
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String folderDate(String str) {
        Log.e("09/09", "folderDate: " + str);
        Date d = new Date(Long.parseLong(str));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(d);
        Log.e("09/09", "folderDate:2 " + date + "\n");

//        return new SimpleDateFormat("yyyy-MMM-dd").format(new Date(Long.parseLong(str) * 1000));
//        if (!Utils.INSTANCE.isValidatEmpty(str)) {
//            strDate = str;
//        }
//        return new SimpleDateFormat("dd MMM").format(new Date(Long.parseLong(strDate) * 1000));
        return date;
    }

    private String videoDate(String str) {
        try {

            return new SimpleDateFormat("dd MMM").format(new Date(Long.parseLong(str) * 1000));
        } catch (Exception e) {
            Log.d("10/03",str+"----"+e.getMessage());
            return "";
        }
    }

    private String size(String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        Log.d("10/03","size----"+str);
        double parseDouble;
        if (!Utils.INSTANCE.isValidatEmpty(str)) {
            parseDouble = Double.parseDouble(str) / 1024.0d;
        } else {
            parseDouble = Double.parseDouble("0") / 1024.0d;
        }
        if (parseDouble < 1024.0d) {
            return decimalFormat.format(parseDouble) + " KB";
        }
        double d = parseDouble / 1024.0d;
        if (d < 1024.0d) {
            return decimalFormat.format(d) + " MB";
        }
        return decimalFormat.format(d / 1024.0d) + " GB";
    }

    public static String detailVideoDate(String str) {
        return new SimpleDateFormat("dd MMM yyyy, HH:mm a").format(new Date(Long.parseLong(str) * 1000));
    }

    public static void shareVideo(final Context context2, String str, String str2) {
        try {
            MediaScannerConnection.scanFile(context2, new String[]{str2}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("video/*");
                    intent.putExtra("android.intent.extra.SUBJECT", str);
                    intent.putExtra("android.intent.extra.STREAM", uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context2.startActivity(Intent.createChooser(intent, "share via:"));
                }
            });
        } catch (Exception unused) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("video/*");
            intent.putExtra("android.intent.extra.SUBJECT", str);
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(str2));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context2.startActivity(Intent.createChooser(intent, "share via:"));
        }
    }

    public static void moveToPrivate(Context context2, String str, String str2) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + parentFolder);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + parentFolder + "/" + str2);
            MediaScannerConnection.scanFile(context2, new String[]{Environment.getExternalStorageDirectory() + "/" + parentFolder + "/" + str2}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    new File(str).delete();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } catch (Exception e2) {
            Log.e("tag", e2.getMessage());
        }
    }

    public static void reMoveToPrivate(Context context2, String str, String str2) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), mainFolder);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/" + str2);
            MediaScannerConnection.scanFile(context2, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/" + str2}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    new File(str).delete();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } catch (Exception e2) {
            Log.e("tag", e2.getMessage());
        }
    }

    public static void copyFile(Context context2, String str, String str2) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + mainFolder + "/Status Saver");
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/Status Saver/" + str2);
            MediaScannerConnection.scanFile(context2, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/Status Saver/" + str2}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } catch (Exception e2) {
            Log.e("tag", e2.getMessage());
        }
    }
}
