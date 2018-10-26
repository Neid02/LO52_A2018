LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

# Sources
LOCAL_SRC_FILES:= \
                  core.c \
                  descriptor.c \
                  io.c \
                  sync.c \
                  os/linux_usbfs.c \
		  os/threads_posix.c

				  
# Includes				  
LOCAL_C_INCLUDES += \
                $(LOCAL_PATH)/ \
		$(LOCAL_PATH)/os/

LOCAL_CFLAGS += $(TOOL_CFLAGS) -DTIMESPEC_TO_TIMEVAL -pthread
LOCAL_LDFLAGS := $(TOOL_LDFLAGS) -pthread

# Output
LOCAL_MODULE := libusb
LOCAL_MODULE_TAGS := optional
LOCAL_PRELINK_MODULE := false
INCLUDE $(BUILD_SHARED_LIBRARY)
