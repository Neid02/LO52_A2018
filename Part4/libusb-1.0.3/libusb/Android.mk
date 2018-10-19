# Android.mk for usblib

# probleme2 :
# Ajouter :
# libusb.so	0x99000000
# au fichier  build/core/prelink-linux-arm.map

commonIncludes:= \
	$(LOCAL_PATH) \
	$(LOCAL_PATH)/os

commonSources:= \	
	os/linux_usbfs.c \
	core.c \
	descriptor.c \
	io.c \
	sync.c
# For the host
LOCAL_PATH:= $ (call my−dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:= \
	$(commonSources)
LOCAL_C_INCLUDES:= \
	$(commonIncludes)
LOCAL_MODULE:= usblib
LOCAL_CFLAGS+= $(TOOL_CFLAGS)
LOCAL_LDFLAGS:= $(TOOL_LDFLAGS) -pthread
include $(BUILD_HOST_STATIC_LIBRARY)

# For the device

include $(CLEAR_VARS)
LOCAL_SRC_FILES:= \
	$(commonSources)
LOCAL_C_INCLUDES:= \
	$(commonIncludes)
LOCAL_MODULE:= libusb
LOCAL_MODULE_TAGS:= optional
include $(BUILD_SHARED_LIBRARY)
