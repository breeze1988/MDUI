/**
 * 
 */

package util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import mduicom.breeze.mdui.application.MduiApplication;

import static android.os.Build.VERSION.SDK_INT;


/**
 * @author liangge
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class DeviceUtil {

	private static final Context mContext;
	private static final float mDensity;
	static {
		mContext = MduiApplication.getmInstance().getApplicationContext();
		mDensity = mContext.getResources().getDisplayMetrics().density;
	}

	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	public static int px2dip(float px) {
		return (int) (px / mDensity + 0.5f);
	}

	public static int dip2px(float dp) {
		return (int) (dp * mDensity + 0.5f);
	}

	public static float sp2px(float sp) {
		return sp * mDensity;
	}

	// public static int getRelativeTop(View myView) {
	// // if (myView.getParent() == myView.getRootView())
	// if (myView.getId() == android.R.id.content)
	// return myView.getTop();
	// else
	// return myView.getTop() + getRelativeTop((View) myView.getParent());
	// }

	// public static int getRelativeLeft(View myView) {
	// // if (myView.getParent() == myView.getRootView())
	// if (myView.getId() == android.R.id.content)
	// return myView.getLeft();
	// else
	// return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	// }

	@SuppressWarnings("unchecked")
	public static <T> T getSystemService(String name) {
		Context context = MduiApplication.getmInstance().getApplicationContext();
		if (context == null) {
			return null;
		} else {
			return (T) context.getSystemService(name);
		}
	}

	// public static int getDensity() {
	// WindowManager wm = getSystemService(Context.WINDOW_SERVICE);
	// DisplayMetrics displayMetric = new DisplayMetrics();
	// wm.getDefaultDisplay().getMetrics(displayMetric);
	// return displayMetric.densityDpi;
	// }

//	public static String getFirstMacAddr() {
//		String s = "";
//
//		WifiManager wifiManager = getSystemService(Context.WIFI_SERVICE);
//		try {
//			WifiInfo info = wifiManager.getConnectionInfo();
//			s = info.getMacAddress();
//			if (s == null) {
//				s = "";
//			}
//		} catch (Exception e) {
//		}
//
//		if (!"".equals(s)) {
//			return s;
//		}
//
//		try {
//			s = BluetoothAdapter.getDefaultAdapter().getAddress();
//			if (s == null) {
//				s = "";
//			}
//		} catch (Exception e) {
//		}
//
//		return s;
//	}

	public static Point getScreen() {
		WindowManager manager = getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point screenResolution = null;

		int x;
		int y;
		if (isHoneycombMR2n()) {
			Point p = new Point();
			display.getSize(p);
			x = p.x;
			y = p.y;
		} else {
			x = display.getWidth();
			y = display.getHeight();
		}

		Configuration configuration = mContext.getResources().getConfiguration();
		if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横屏
			screenResolution = new Point(Math.max(x, y), Math.min(x, y));
		} else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 竖屏
			screenResolution = new Point(Math.min(x, y), Math.max(x, y));
		}

		return screenResolution;
	}

	/**
	 * 获取可用内存信息
	 * 
	 * @return 剩余内存（单位Byte）
	 */
	public static long getAvailMemory() {
		ActivityManager am = getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return Math.abs(mi.availMem);
	}

	/**
	 * 获取总内存
	 * 
	 * @return 单位Byte
	 */
	public static long getTotalMemory() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
			String content = reader.readLine();// meminfo
			String[] array = content.split("\\s+");
			return Long.valueOf(array[1]).longValue() * 1024;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			StreamUtils.closeIO(reader);
		}

	}

	/**
	 * 获取手机可用存储空间
	 * 
	 * @return
	 */
	public static long getSystemAvailabelSpace() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long availableBlocks;
		if (isJellyBeanMR2()) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		}

		return blockSize * availableBlocks;
	}

	/**
	 * 获取手机总存储空间（单位Byte）
	 * 
	 * @return
	 */
	public static long getSystemTotalSpace() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long bSize;
		long bCount;
		if (isJellyBeanMR2()) {
			bSize = stat.getBlockSizeLong();
			bCount = stat.getBlockCountLong();
		} else {
			bSize = stat.getBlockSize();
			bCount = stat.getBlockCount();
		}
		return bSize * bCount;
	}

	/**
	 * 获取存储卡信息
	 * 
	 * @return long[0]:总空间（单位Byte） long[1]:剩余空间（单位Byte）
	 */
	public static long[] getSDCardMemory() {
		long[] sdCardInfo = new long[2];
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());

			long bSize;
			long bCount;
			long availBlocks;
			if (isJellyBeanMR2()) {
				bSize = sf.getBlockSizeLong();
				bCount = sf.getBlockCountLong();
				availBlocks = sf.getAvailableBlocksLong();
			} else {
				bSize = sf.getBlockSize();
				bCount = sf.getBlockCount();
				availBlocks = sf.getAvailableBlocks();
			}

			sdCardInfo[0] = bSize * bCount;
			sdCardInfo[1] = bSize * availBlocks;
		}
		return sdCardInfo;
	}

	/**
	 * 获取所有扩展存储空间信息
	 * 
	 * @return
	 */
//	public static ArrayList<ExternalStorageInfo> getAllExternalStorageMemory() {
//		ArrayList<String> storagePaths = getAllExternalStoragePath();
//		if (storagePaths == null || storagePaths.size() == 0) {
//			return null;
//		}
//
//		ArrayList<ExternalStorageInfo> list = new ArrayList<ExternalStorageInfo>();
//		ExternalStorageInfo info;
//		StatFs sf;
//		for (String path : storagePaths) {
//			info = new ExternalStorageInfo();
//			sf = new StatFs(path);
//
//			long bSize;
//			long bCount;
//			long availBlocks;
//			if (isJellyBeanMR2()) {
//				bSize = sf.getBlockSizeLong();
//				bCount = sf.getBlockCountLong();
//				availBlocks = sf.getAvailableBlocksLong();
//			} else {
//				bSize = sf.getBlockSize();
//				bCount = sf.getBlockCount();
//				availBlocks = sf.getAvailableBlocks();
//			}
//
//			info.totalMemory = bSize * bCount;
//			info.availMemory = bSize * availBlocks;
//			info.path = path;
//			list.add(info);
//		}
//		return list;
//	}

	/**
	 * 震动提示
	 * 
	 * @param milliseconds
	 *            (震动时间)
	 */
	public static void getVibrator(long milliseconds) {
		try {
			Vibrator mVibrator = getSystemService(Service.VIBRATOR_SERVICE);
			mVibrator.vibrate(milliseconds);
		} catch (Exception e) {
		}
	}

	public static String getSDCardDir() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return "";
	}

	/**
	 * 获取所有外部存储设备
	 * 
	 * @return
	 */
	public static ArrayList<String> getAllExternalStoragePath() {
		ArrayList<String> storagePaths = new ArrayList<String>();
		
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			return storagePaths;
		}

		if (isIceCreamSandwich()) {
			try {
				StorageManager storageManager = getSystemService(Context.STORAGE_SERVICE);
				Method method = StorageManager.class.getMethod("getVolumePaths");
				String[] storagePathList = (String[]) method.invoke(storageManager);
				if (storagePathList == null || storagePathList.length == 0) {
					return null;
				}

				method = StorageManager.class.getMethod("getVolumeState", String.class);
				String st;
				for (String sp : storagePathList) {
					st = (String) method.invoke(storageManager, sp);
					if (Environment.MEDIA_MOUNTED.equals(st) && new File(sp).exists()) {
						storagePaths.add(sp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (storagePaths.isEmpty()) {
			File mntFile = new File("/mnt");
			File[] files = mntFile.listFiles(new FileFilter() {
				@Override
				public boolean accept(File rf) {
					if (rf.exists() && rf.canRead() && rf.canWrite() && rf.isDirectory()
							&& !rf.getName().startsWith(".")
							&& rf.getPath().indexOf("legacy") == -1) {// 平板此目录为重复目录
						return true;
					}
					return false;
				}
			});
			
			if (files == null || files.length == 0) {
				storagePaths.add(Environment.getExternalStorageDirectory().getPath());
			} else {
				for (File f : files) {
					storagePaths.add(f.getPath());
				}
			}
		}

		return storagePaths;
	}

	/**
	 * 获取外部存储路径(用于区分内置存储卡还是外置存储卡)
	 * 
	 * @return null：没有外部存储(包括外置存储卡没有挂载)或只有内置存储卡
	 */
	public static String getExternalStoragePath() {
		if (isIceCreamSandwich()) {
			try {
				StorageManager storageManager = getSystemService(Context.STORAGE_SERVICE);
				Method method = StorageManager.class.getMethod("getVolumePaths");
				String[] storagePathList = (String[]) method.invoke(storageManager);
				if (storagePathList != null && storagePathList.length >= 2) {
					if (storagePathList[1] == null) {
						return null;
					}
					method = StorageManager.class.getMethod("getVolumeState", String.class);
					String state = (String) method.invoke(storageManager, storagePathList[1]);
					if (Environment.MEDIA_MOUNTED.equals(state)) {
						return storagePathList[1];
					}
				} else if (!isExternalStorageEmulated()) {
					return Environment.getExternalStorageDirectory().getPath();
				}
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return Environment.getExternalStorageDirectory().getPath();
			}
		} else if (isKindleFire()) {
			return null;
		} else {
			return Environment.getExternalStorageDirectory().getPath();
		}
	}

	/**
	 * 判断外部存储和内部存储是否同一存储
	 * 
	 * @return true：同一存储，false：不是同一存储
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean isExternalStorageEmulated() {
		if (DeviceUtil.isIceCreamSandwich()) {
			try {
				Class localClass = Class.forName("android.os.Environment");
				return ((Boolean) localClass.getMethod("isExternalStorageEmulated", new Class[0])
						.invoke(localClass.getClass(), new Object[0])).booleanValue();
			} catch (Exception e) {
				Log.d("","isExternalStorageEmulated error:" + e.getMessage());
			}
		}
		return false;
	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	// public static String getTimeZone() {
	// return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT,
	// Locale.ENGLISH);
	// }

	/**
	 * 获取手机国家号码
	 * 
	 * @return
	 */
	public static String getMCC(Context context) {
		TelephonyManager TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE);
		String simOperator = TelephonyMgr.getSimOperator();
		if (simOperator != null && simOperator.length() > 3) {
			return simOperator.substring(0, 3);
		}
		return "";
	}

	/**
	 * 获取手机运营商代码
	 * 
	 * @return
	 */
	public static String getMNC(Context context) {
		TelephonyManager TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE);
		String simOperator = TelephonyMgr.getSimOperator();
		if (simOperator != null && simOperator.length() > 3) {
			return simOperator.substring(3, simOperator.length());
		}
		return "";
	}

	/**
	 * sim卡是否可用
	 * 
	 * @return
	 */
	public static boolean hasSIMcard() {
		TelephonyManager mTelephonyManager = getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
	}

	public static boolean isSIMcardUnavailable() {
		TelephonyManager mTelephonyManager = getSystemService(Context.TELEPHONY_SERVICE);
		int simState = mTelephonyManager.getSimState();
		if (simState == TelephonyManager.SIM_STATE_ABSENT
				|| simState == TelephonyManager.SIM_STATE_UNKNOWN) {
			return true;
		}

		return mTelephonyManager.getNetworkType() == 0;
	}

	/**
	 * 是否有Phone服务
	 * 
	 * @return
	 */
	public static boolean hasPhone() {
		return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
	}

	/**
	 * 是否有蓝牙模块
	 * 
	 * @return
	 */
	public static boolean hasBluetooth() {
		return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
	}

	/**
	 * 是否有NFC模块
	 * 
	 * @return
	 */
	public static boolean hasNFC() {
		return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
	}

	/**
	 * 是否有GPS模块
	 * 
	 * @return
	 */
	public static boolean hasGps() {
		return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
	}

	/**
	 * 是否是飞行模式
	 * 
	 * @return
	 */
	public static boolean isAirModeOn() {
		return (Settings.System.getInt(mContext.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
	}

	public static boolean isLGDevice() {
		if (("LGE".equalsIgnoreCase(Build.BRAND)) || ("LG-".equalsIgnoreCase(Build.MODEL))) {
			return SDK_INT == 10;
		}
		return false;
	}

	public static boolean isSamsungDevice() {
		return "samsung".equalsIgnoreCase(Build.BRAND);
	}

	public static boolean isMotoDevice() {
		return "MOTO".equalsIgnoreCase(Build.BRAND);
	}

	public static boolean isKindleFire() {
		return "Kindle Fire".equals(Build.MODEL);
	}

	/**
	 * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
	 * 
	 * @return 平板返回 True，手机返回 False
	 */
	public static boolean isTablet() {
		Configuration configuration = mContext.getResources().getConfiguration();
		return (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isTabletDevice() {
		TelephonyManager telephony = getSystemService(Context.TELEPHONY_SERVICE);
		return telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
	}

	/**
	 * 获取版本信息
	 * 
	 * @return String[0]:内核version String[1]:SDK version String[2]:手机名
	 */
	public static String[] getDeviceVersion() {
		String[] version = { "N/A", "N/A", "N/A" };
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/version"));
			String[] array = reader.readLine().split("\\s+");
			version[0] = array[2];
		} catch (Exception e) {
		} finally {
			StreamUtils.closeIO(reader);
		}
		version[1] = Build.VERSION.RELEASE;
		// MANUFACTURER:手机厂商,MODEL:手机型号
		version[2] = Build.MANUFACTURER + " " + Build.MODEL;
		return version;
	}

	@SuppressWarnings("resource")
	public static String getCpuName() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/proc/cpuinfo"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Serial")) {
					return line.substring(line.indexOf(":") + 1, line.length()).trim();
				}
			}
		} catch (Exception e) {
		} finally {
			StreamUtils.closeIO(br);
		}

		return null;
	}

	/**
	 * 获取CPU信息
	 * 
	 * @return String[0]:CPU型号 String[1]:CPU核心数
	 */
	public static String[] getCpuInfo() {
		String[] cpuInfos = new String[] { "N/A", "1" };
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/proc/cpuinfo"));
			int i = 0;
			String text;
			while ((text = br.readLine()) != null) {
				String[] array = text.split(":");
				if (array.length < 2) {
					continue;
				}
				if (array[0].trim().equalsIgnoreCase("Processor")) {
					if (i == 0) {
						cpuInfos[0] = array[1].trim();
					}
					i++;
				}
			}
			if (i > 1 && i % 2 != 0) {
				i = i - 1;
			}
			cpuInfos[1] = String.valueOf(i);
		} catch (Exception e) {
		} finally {
			StreamUtils.closeIO(br);
		}
		return cpuInfos;
	}

	/**
	 * 需：<uses-permission android:name="android.permission.READ_PHONE_STATE"
	 * />权限
	 * 
	 * @return
	 */
	public static String getIMEI() {
		if (!hasPhone()) {
			return null;
		}

		TelephonyManager tm = getSystemService(Context.TELEPHONY_SERVICE);
		String id = tm.getDeviceId();
		if (id == null || id.length() == 0) {
			id = tm.getSubscriberId();
		}

		return id;
	}

	/**
	 * 获取手机网络运营商名字
	 * 
	 * @return
	 */
	public static String getCarrier() {
		if (!hasPhone()) {
			return null;
		}
		TelephonyManager tm = getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkOperatorName();
	}

	// 在CMDA网络下，结果不可靠。通过getPhoneType判断是否在CDMA网络下。
	// 通过获取String imsi = telephonyManager.getSubscriberId();
	// 参照http://en.wikipedia.org/wiki/Mobile_Network_Code
	// 根据WIKI中的Code识别

	// 返回网络类型
	public static String getNetworkType() {
		String unknownType = "";
		try {
//			return SystemProperties.get("gsm.network.type", unknownType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unknownType;
	}

	public static String getDeviceInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("build_sdk_version=").append(SDK_INT).append("\r\n");
		sb.append("build_brand=").append(Build.BRAND).append("\r\n");
		sb.append("build_model=").append(Build.MODEL).append("\r\n");
		sb.append("build_manufacturer=").append(Build.MANUFACTURER).append("\r\n");
//		VersionConfig config = VersionConfig.getInstance();
//		sb.append("AMC Old Version=").append(config.getAMCOldVersion()).append("\r\n");
//		sb.append("AMC New Version=").append(config.getCurrentVersion()).append("\r\n");
//		sb.append("DeviceId=").append(IDHelper.getUDID()).append("\r\n");
		sb.append("ABI=").append(Arrays.toString(getSupportABIs())).append("\r\n");
		return sb.toString();
	}
	
	public static String[] getSupportABIs() {
		if (Build.VERSION.SDK_INT >= 21) {
			return Build.SUPPORTED_ABIS;
		} else {
			return new String[] { Build.CPU_ABI };
		}
	}

	public static boolean checkSdCardExist() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * API >= 11
	 * 
	 * @return
	 */
	public static boolean isHoneycomb() {
		return SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * API >= 13
	 * 
	 * @return
	 */
	public static boolean isHoneycombMR2n() {
		return SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
	}

	/**
	 * API >= 14
	 * 
	 * @return
	 */
	public static boolean isIceCreamSandwich() {
		return SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 * API >= 15
	 * 
	 * @return
	 */
	public static boolean isIceCreamSandwichMR1() {
		return SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}

	/**
	 * API >= 16
	 * 
	 * @return
	 */
	public static boolean isJellyBean() {
		return SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	/**
	 * API >= 17
	 * 
	 * @return
	 */
	public static boolean isJellyBeanMR1() {
		return SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	/**
	 * API >= 18
	 * 
	 * @return
	 */
	public static boolean isJellyBeanMR2() {
		return SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
	}

	/**
	 * API >= 19
	 * 
	 * @return
	 */
	public static boolean isKitkat() {
		return SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	/**
	 * API >= 21
	 * 
	 * @return
	 */
	public static boolean isLollipop() {
		return SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}

	/**
	 * API >= 23
	 * 
	 * @return
	 */
	public static boolean isMarshmallow() {
		return SDK_INT >= 23;
	}

	// public static int getAppVerisonCode() {
	// try {
	// return PackageManagerHelper.getPackageInfo(mContext.getPackageName(),
	// 0).versionCode;
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// }
	// return 0;
	// }

	public static boolean canWrite(String path) {
		return canWrite(new File(path));
	}

	public static boolean canWrite(File file) {
		if (file.exists() && file.canRead() && file.canWrite()) {
			File testFile = new File(file, "writableTest_" + System.currentTimeMillis());
			try {
				if (testFile.mkdir()) {
					testFile.delete();
					return true;
				}
			} catch (Exception e) {
			}
		}

		return false;
	}
}
