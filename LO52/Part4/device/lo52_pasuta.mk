$(call inherit-product, device/linaro/hikey/hickey.mk)
PRODUCT_PACKAGES += libusb
PRODUCT_PROPERTY_OVERRIDES := ro.hw=lo52 net.dns1=8.8.8.8 net.dns2=4.4.4.4
DEVICE_PACKAGE_OVERLAYS:= $(LOCAL_DIR)/overlay
PRODUCT_NAME:= lo52_pasuta
PRODUCT_BRAND:= lo52_pasuta
PRODUCT_MODEL:= lo52_pasuta
PRODUCT_DEVICE:= lo52_pasuta
