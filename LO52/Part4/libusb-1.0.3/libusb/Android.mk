#Android.mkforlibusb.
#

commonSources:=\
core.c\
descriptor.c\
io.c\
libusbi.h\
sync.c\
linux_usbfs.c\
linux_usbfs.h


LOCAL_PATH:= $(call my_dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
		$(commonSources)

LOCAL_C_INCLUDES:=\
		os/

LOCAL_MODULE:= libusb

LOCAL_MODULE_TAGS:= optional

#correction de l'erreur 1
LOCAL_C_FLAGS+= -pthread -DTIMESPEC_TO_TIMEVAL

LOCAL_LD_FLAGS:= −lstdc++ −lc

#correction de l'erreur 2
LOCAL_PRELINK_MODULE:= false

include $(BUILD_SHARED_LIBRARY)
