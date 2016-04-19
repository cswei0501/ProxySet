@echo off
::设置环境变量, 如果你的NDK路径和我的不一样, 请修改正确
set PATH=%PATH%;"E:\Android\android-ndk-r10e";
::echo %PATH%

if "%1" == "clean"  (  
    rd /s /q libs
	rd /s /q obj
	exit /b 0
)

ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./jni/Android.mk NDK_APP_APPLICATION_MK=./jni/Application.mk

::编译成动态库
::NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=Android.mk APP_ABI=all