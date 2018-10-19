cmd_arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb := mkdir -p arch/arm64/boot/dts/hisilicon/ ; /home/cardoso/Bureau/LO52/hikey/aarch64-linux-android-4.9/bin/aarch64-linux-android-gcc -E -Wp,-MD,arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.d.pre.tmp -nostdinc -I./arch/arm64/boot/dts -I./arch/arm64/boot/dts/include -I./drivers/of/testcase-data -I./include -undef -D__DTS__ -x assembler-with-cpp -o arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.dts.tmp arch/arm64/boot/dts/hisilicon/hi6220-hikey.dts ; ./scripts/dtc/dtc -O dtb -o arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb -b 0 -i arch/arm64/boot/dts/hisilicon/  -d arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.d.dtc.tmp arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.dts.tmp ; cat arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.d.pre.tmp arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.d.dtc.tmp > arch/arm64/boot/dts/hisilicon/.hi6220-hikey.dtb.d

source_arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb := arch/arm64/boot/dts/hisilicon/hi6220-hikey.dts

deps_arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb := \
  arch/arm64/boot/dts/hisilicon/hikey-pinctrl.dtsi \
  arch/arm64/boot/dts/include/dt-bindings/pinctrl/hisi.h \
  arch/arm64/boot/dts/hisilicon/hikey-gpio.dtsi \
  arch/arm64/boot/dts/hisilicon/hi6220.dtsi \
  arch/arm64/boot/dts/include/dt-bindings/interrupt-controller/arm-gic.h \
  arch/arm64/boot/dts/include/dt-bindings/interrupt-controller/irq.h \
  arch/arm64/boot/dts/include/dt-bindings/clock/hi6220-clock.h \
  arch/arm64/boot/dts/include/dt-bindings/thermal/thermal.h \

arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb: $(deps_arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb)

$(deps_arch/arm64/boot/dts/hisilicon/hi6220-hikey.dtb):
