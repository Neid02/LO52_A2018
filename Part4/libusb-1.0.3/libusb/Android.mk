LOCAL_PATH:=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:=\
	core.c\
	descriptor.c\
	io.c\
	sync.c\
	linux_usbfs.c
LOCAL_C_INCLUDES:=\
	/os
LOCAL_MODULE:=libusb-lib
LOCAL_MODULE_TAGS:=optional
LOCAL_PRELINK_MODULE:=false
include $(BUILD_SHARED_LIBRARY)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:=\
	core.c\
	descriptor.c\
	io.c\
	sync.c\
	linux_usbfs.c
LOCAL_C_INCLUDES:=\
	/os
LOCAL_MODULE:=libusb-lib-static
LOCAL_MODULE_TAGS:=optional
LOCAL_PRELINK_MODULE:=false
include $(BUILD_STATIC_LIBRARY)
