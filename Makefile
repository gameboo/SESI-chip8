ANDROID_API_LVL=8
ANDROID_JAR=$(ANDROID_SDK_HOME)/platforms/android-$(ANDROID_API_LVL)/android.jar
ADB=$(shell which adb)

JAVA_SRC=$(shell find src/ -name *.java -type f)

BUILD_DIR=build
CLASS_DIR=$(BUILD_DIR)/classes
DEX_DIR=$(BUILD_DIR)/dex
DEX_FILE=app.dex
RES_DIR=res
ASSETS_DIR=assets
RES_OUTPUT_FILE=$(BUILD_DIR)/res
ANDROID_MANIFEST=AndroidManifest.xml
PACKAGE=SESI.chip8

SRC=src/SESI/chip8
JFILES1=MAIN_CFILES     = $(foreach d,$(SRC)/,$(wildcard $(d)/*.java))
JFILES2=MAIN_CFILES     = $(foreach d,$(SRC)/utils/,$(wildcard $(d)/*.java))
JFILES3=MAIN_CFILES     = $(foreach d,$(SRC)/utils/screen/,$(wildcard $(d)/*.java))
JFILES4=MAIN_CFILES     = $(foreach d,$(SRC)/simu/,$(wildcard $(d)/*.java))

JAVAFILES=$(JFILES1) $(JFILES2) $(JFILES3) $(JFILES4)

APP_NAME=app

JAVA_COMPILER=javac

all: $(BUILD_DIR)/$(APP_NAME).apk

$(BUILD_DIR)/$(APP_NAME).apk : $(DEX_DIR)/$(DEX_FILE)
	apkbuilder $(BUILD_DIR)/$(APP_NAME).apk -f $(DEX_DIR)/$(DEX_FILE) -z $(RES_OUTPUT_FILE)

$(DEX_DIR)/$(DEX_FILE):JAVATERIES
	dx --dex --output=$(DEX_DIR)/$(DEX_FILE) $(CLASS_DIR)

JAVATERIES:AAPTERIES $(JAVA_FILES)
	$(JAVA_COMPILER) -classpath $(ANDROID_JAR) -d $(CLASS_DIR) $(JAVA_SRC) $(BUILD_DIR)/R.java

AAPTERIES:$(ANDROID_MANIFEST)
	mkdir -p $(BUILD_DIR)
	mkdir -p $(CLASS_DIR)
	mkdir -p $(DEX_DIR)
	aapt p -f -M $(ANDROID_MANIFEST) -S $(RES_DIR) -A $(ASSETS_DIR) -J $(BUILD_DIR) -I $(ANDROID_JAR) -F $(RES_OUTPUT_FILE)

startadb:
	sudo $(ADB) start-server

emu:
	$(ADB) -e install $(BUILD_DIR)/$(APP_NAME).apk

remu:
	$(ADB) -e uninstall $(PACKAGE)

dev:
	$(ADB) -d install $(BUILD_DIR)/$(APP_NAME).apk

rdev:
	$(ADB) -d uninstall $(PACKAGE)
	


.PHONY: clean

clean:
	rm -rf $(BUILD_DIR)
