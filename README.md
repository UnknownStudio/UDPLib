# UDPLib [![CircleCI](https://circleci.com/gh/UnknownStudio/UDPLib.svg?style=svg)](https://circleci.com/gh/UnknownStudio/UDPLib)
**Unknown Domain Spigot Plugin Library**

By using this lib, you can easily develop your plugin with the help of such feature:
* Annotation Command Support (Build your command with @Command)
* Inventory UI (Custom behaviors of inventory's slot)
* Area Manager (Allow you to manager area intuitively)
* Item/Block Helper
* NMS Wapper (An easy and safe way to using nsme)
* Utils
* ....

## Usage
[Github Wiki](https://github.com/UnknownStudio/UDPLib/wiki) 


## Download
**To get this projectf rom JitPack.io into your build:**


Maven:
1. Add the JitPack repository to your build file
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
	</repositories>
```

2. Add the dependency
```xml
	<dependency>
	    <groupId>com.github.UnknownStudio</groupId>
	    <artifactId>UDPLib</artifactId>
	    <version>1.0.0-Release</version>
	</dependency>
```

Gradle:
1. Add this in your root build.gradle at the end of repositories
```
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

2. Add the dependency
```
	dependencies {
		compile 'com.github.User:Repo:Tag'
	}
```
