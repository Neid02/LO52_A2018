$(call inherit-product, device/ti/linaro/full_linaro.mk)

PRODUCT_PACKAGES += libusb

PRODUCT_PROPERTY_OVERRIDE := ro.hw = lo52
PRODUCT_PROPERTY_OVERRIDE := net.dns1 = 8.8.8.8
PRODUCT_PROPERTY_OVERRIDE := net.dns2 = 4.4.4.4

DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52_mamassi_maillard/overlay

PRODUCT_NAME := lo52_mamassi_maillard
PRODUCT_BRAND := lo52_mamassi_maillard
PRODUCT_MODEL := lo52_mamassi_maillard
PRODUCT_DEVICE := lo52_mamassi_maillard
