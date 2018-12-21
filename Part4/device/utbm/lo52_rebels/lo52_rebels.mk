$(call inherit-product, device/linaro/hikey/hikey.mk)
PRODUCT_PROPERTY_OVERRIDES := \
						ro.hw = lo52 \
						net.dns1 = 8.8.8.8 \
						net.dns2 = 4.4.4.4
DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52_rebels/overlay
PRODUCT_PACKAGES += libusb
PRODUCT_NAME := lo52_rebels
PRODUCT_BRAND := lo52_rebels
PRODUCT_DEVICE := lo52_rebels
PRODUCT_MODEL := lo52_rebels
