@echo off
::���û�������, ������NDK·�����ҵĲ�һ��, ���޸���ȷ
set PATH=%PATH%;"E:\Android\android-ndk-r10e";
::echo %PATH%

if "%1" == "clean"  (  
    rd /s /q libs
	rd /s /q obj
	exit /b 0
)

ndk-build NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=./Android.mk

::����ɶ�̬��
::NDK_PROJECT_PATH=. APP_BUILD_SCRIPT=Android.mk APP_ABI=all