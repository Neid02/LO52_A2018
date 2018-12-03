$(call inherit_product device/linaro/hikey/hikey.mk
	PRODUCT_PACKAGES += libusb
	PRODUCT_PROPERTY_OVERRIDERS := ro.hw=lo52 net.dns1=8.8.8.8 net.dns2=4.4.4.4
	DEVICE_PACKAGE_OVERLAY := device/utbm/lo52adurycsanchez/overlay	
	PRODUCT_NAME := lo52adurycsanchez
	PRODUCT_BRAND := lo52adurycsanchez
	PRODUCT_MODEL := lo52adurycsanchez
	PRODUCT_DEVICE := lo52adurycsanchez
