ANDROID_API_LVL=7
ANDROID_JAR=$(ANDROID_SDK_HOME)/platforms/android-$(ANDROID_API_LVL)/android.jar

JAVA_SRC=$(shell find src/ -name *.java -type f)

BIN_DIR=bin
CLASS_DIR=$(BIN_DIR)/classes
DEX_DIR=$(BIN_DIR)/dex
DEX_FILE=app.dex
RES_FILE=$(BIN_DIR)/res
ANDROID_MANIFEST=AndroidManifest.xml

APP_NAME=app

JAVA_COMPILER=javac

all:
	mkdir -p $(BIN_DIR)
	mkdir -p $(CLASS_DIR)
	mkdir -p $(DEX_DIR)
	$(JAVA_COMPILER) -classpath $(ANDROID_JAR) -d $(CLASS_DIR) $(JAVA_SRC)
	dx --dex --output=$(DEX_DIR)/$(DEX_FILE) $(CLASS_DIR)
	aapt p -M $(ANDROID_MANIFEST) -I $(ANDROID_JAR) -F $(RES_FILE)
	apkbuilder $(BIN_DIR)/$(APP_NAME).apk -f $(DEX_DIR)/$(DEX_FILE) -z $(RES_FILE)

.PHONY: clean

clean:
	rm -rf bin/
