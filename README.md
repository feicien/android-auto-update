Android应用自动更新库(android-auto-update)
===================


该library项目实现了软件版本检查，apk文件下载，软件安装（Android app update checker,download and install apk）支持API 8+


#### 1.导入library项目 ####

提供2种版本检查方式,在你的项目中添加以下代码即可

- 使用Dialog
   
    	`UpdateChecker.checkForDialog(this);`

- 使用Notification

	`UpdateChecker.checkForNotification(this);`



#### 2.添加权限 ####

- 添加访问网络的权限

	`<uses-permission android:name="android.permission.INTERNET" />`

- 添加写SDCard权限（可选，非必须）

	如果添加这个权限 apk下载在sdcard中的Android/data/包名/cache目录下 否则下载到 内存中的 /data/data/包名/cache中

	`<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`

#### 3.截图 ####
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/sample.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/sample_htc.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/dialog.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/dialog_htc.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/notification.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/notification_avd.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/downloading.png)
![screenshot](https://raw.github.com/feicien/android-auto-update/master/screenshots/downloading_avd.png)


#### 4.使用与参考的开源项目 ####



1. [android-styled-dialogs](https://github.com/inmite/android-styled-dialogs "https://github.com/inmite/android-styled-dialogs") 使用该项目，可以在api 8+上显示 holo 风格的对话框，其它选择
，当然你也可以使用其它的开源项目比如：[ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock "https://github.com/JakeWharton/ActionBarSherlock") 和 [HoloEverywhere](https://github.com/Prototik/HoloEverywhere "https://github.com/Prototik/HoloEverywhere")


2. [UpdateChecker](https://github.com/rampo/UpdateChecker "https://github.com/rampo/UpdateChecker") 该项目检查的是google play上的应用，如果有更新打开google Play,不提供下载apk的功能

 
