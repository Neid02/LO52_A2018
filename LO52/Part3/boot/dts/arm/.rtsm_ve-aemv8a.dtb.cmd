cmd_arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb := mkdir -p arch/arm64/boot/dts/arm/ ; /home/cardoso/Bureau/LO52/hikey/aarch64-linux-android-4.9/bin/aarch64-linux-android-gcc -E -Wp,-MD,arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.d.pre.tmp -nostdinc -I./arch/arm64/boot/dts -I./arch/arm64/boot/dts/include -I./drivers/of/testcase-data -I./include -undef -D__DTS__ -x assembler-with-cpp -o arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.dts.tmp arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dts ; ./scripts/dtc/dtc -O dtb -o arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb -b 0 -i arch/arm64/boot/dts/arm/  -d arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.d.dtc.tmp arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.dts.tmp ; cat arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.d.pre.tmp arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.d.dtc.tmp > arch/arm64/boot/dts/arm/.rtsm_ve-aemv8a.dtb.d

source_arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb := arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dts

deps_arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb := \
  arch/arm64/boot/dts/arm/rtsm_ve-motherboard.dtsi \

arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb: $(deps_arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb)

$(deps_arch/arm64/boot/dts/arm/rtsm_ve-aemv8a.dtb):
