ANDROID_API_LVL=8
ANDROID_JAR=$(ANDROID_SDK_HOME)/platforms/android-$(ANDROID_API_LVL)/android.jar
ADB=$(shell which adb)

JAVA_SRC=$(shell find src/ -name *.java -type f)

BUILD_DIR=build
CLASS_DIR=$(BUILD_DIR)/classes
DEX_DIR=$(BUILD_DIR)/dex
DEX_FILE=app.dex
RES_DIR=res
RES_OUTPUT_FILE=$(BUILD_DIR)/res
ANDROID_MANIFEST=AndroidManifest.xml
PACKAGE=SESI.chip8

APP_NAME=app

JAVA_COMPILER=javac

all: $(BUILD_DIR)/$(APP_NAME).apk

$(BUILD_DIR)/$(APP_NAME).apk : $(DEX_DIR)/$(DEX_FILE)
	apkbuilder $(BUILD_DIR)/$(APP_NAME).apk -f $(DEX_DIR)/$(DEX_FILE) -z $(RES_OUTPUT_FILE)

$(DEX_DIR)/$(DEX_FILE):JAVATERIES
	dx --dex --output=$(DEX_DIR)/$(DEX_FILE) $(CLASS_DIR)

JAVATERIES:AAPTERIES
	$(JAVA_COMPILER) -classpath $(ANDROID_JAR) -d $(CLASS_DIR) $(JAVA_SRC) $(BUILD_DIR)/R.java

AAPTERIES:$(ANDROID_MANIFEST)
	mkdir -p $(BUILD_DIR)
	mkdir -p $(CLASS_DIR)
	mkdir -p $(DEX_DIR)
	aapt p -f -M $(ANDROID_MANIFEST) -S $(RES_DIR) -J $(BUILD_DIR) -I $(ANDROID_JAR) -F $(RES_OUTPUT_FILE)



emu:all
	$(ADB) -e install $(BUILD_DIR)/$(APP_NAME).apk

remu:
	$(ADB) -e uninstall $(PACKAGE)
	


.PHONY: clean

clean:
	rm -rf $(BUILD_DIR)
