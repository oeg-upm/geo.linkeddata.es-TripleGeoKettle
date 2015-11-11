#geo.linkeddata.es-TripleGeoKettle
Repository where the integration of TripleGeo and GeoKettle is performed, used in the [geo.linkeddata.es](https://github.com/oeg-upm/geo.linkeddata.es-termite) project.

##Prerequisites
###Common requirements for any platform
* Java Runtime Environment (JRE) version 1.8 (also called 8.0). You can download a JRE for free [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
* GeoKettle (GUI version 2.5). You can download it for free [here](http://sourceforge.net/projects/geokettle/files/geokettle-2.x/2.5/).

###Only for Mac OS
* Java Runtime Environment (JRE) version 1.6 (also called 6.0). You can download this package for free [here](https://support.apple.com/kb/DL1572?locale=es_ES).

##Installation
###Common steps for any platform
1. Download your version [tripleGeoplugin.zip](./download/tripleGeoplugin.zip).
2. Point at GeoKettle steps folder (by default):
 * Linux: ```/opt/geokettle/plugins/steps```
 * Windows 64 bits: ```C:\Program Files (x86)\GeoKettle\plugins\steps```
 * Windows 32 bits: ```C:\Program Files\GeoKettle\plugins\steps```
 * Mac OS: ```/Applications/GeoKettle.app/Contents/Resources/Java/plugins/steps```
3. Unzip it in this folder. (result will be steps/tripleGeoplugin)
4. Enjoy it if you are not installing on Mac OS.

###Only for Mac OS
At this point you need to know that our team have tried to use ad-hoc options to fix a problem between current JRE installed on Mac OS, JRE 1.6 legacy and GeoKettle bundle. This plugin use [Apache Jena](https://jena.apache.org/download/index.cgi) which is compatible with Java 1.8, so there is version mismatch. For achieve the goal, we need to change options (use 64 bits compiler for catch last version of JRE) and way to use the application (shell instead of shortcut). If we had Geokettle's source code, we could compile it again and create better [bundle](https://github.com/tofi86/universalJavaApplicationStub) compatible with Java 1.6 or above because the downloaded version use JRE 1.6 (compiled bundle and not configurable).

0. Set JAVA_HOME [DIY here](http://www.mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/)
1. Point at GeoKettle root folder.
 * ```/Applications/GeoKettle.app/Contents```
2. Edit Info.plist (Open with ...)
 * Change key JVMVersion to ```1.6+```
 * Change key JVMArchs to ```x86_64```
 * Add to key VMOptions value ```-d64 ``` before ```-Xmx512m```
3. Point at GeoKettle folder.
 * ```/Applications/GeoKettle.app/Contents/Resources/Java```
4. Edit spoon.sh (Open with ...)
 * Change this line ```$JAVA_BIN -d32 -Xst ...``` to ```$JAVA_BIN -d64 -Xst ...```
5. Download SWT Library (Mac OS) from Eclipse website [here](https://www.eclipse.org/swt/)
6. Unzip it in the libswt/osx directory of GeoKettle and replace old version.
7. Change permissions of geokettle script
 * ```sudo chmod +x /Applications/GeoKettle.app/Contents/Resources/Java/geokettle.sh```

From now, you must launch geokettle from shell: ```./Applications/GeoKettle.app/Contents/Resources/Java/geokettle.sh```.

##Screenshot

![TripleGEO](./images/screenshot.png)

##License

The contents of this project are licensed under the [Apache License](./LICENSE)
