$(call inherit-product, device/linaro/hikey/hikey.mk)
PRODUCT_PACKAGES+=libusb
PRODUCT_PROPERTY_OVERRIDES:=\
	ro.hw=lo52\
	net.dns1=8.8.8.8\
	net.dns2=4.4.4.4
DEVICE_PACKAGE_OVERLAYS:=device/overlay
PRODUCT_NAME:=lo52_reverse_slice
PRODUCT_BRAND:=lo52_reverse_slice
PRODUCT_MODELE:=lo52_reverse_slice
PRODUCT_DEVICE:=lo52_reverse_slice
