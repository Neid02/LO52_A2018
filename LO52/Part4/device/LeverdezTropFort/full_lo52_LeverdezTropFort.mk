PRODUCT PACKAGES += \
	libusb \

PRODUCT PROPERTY OVERRIDES := \
ro.hw = lo52 \
net.dns1 = 8.8.8.8 \
net.dns2 = 8.8.4.4 \
sym_keyboard_delete.png = $(???)

$(call inherit-product, $../../../Part3/hikey/hikey-linaro/full_linaro.mk)
#
# Overrides
PRODUCT_NAME := full_lo52_LeverdezTropFort
PRODUCT_DEVICE := lo52_LeverdezTropFort
