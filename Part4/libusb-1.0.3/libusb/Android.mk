LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \

io.c \
core.c \
sync.c \
descriptor.c \
os/linux_usbfs.c \

LOCAL_C_INCLUDES += \
external/libusb-1.0.3/ \
external/libusb-1.0.3/libusb/ \
external/libusb-1.0.3/libusb/os