cmd_arch/arm64/boot/dts/apm/apm-mustang.dtb := mkdir -p arch/arm64/boot/dts/apm/ ; /home/cardoso/Bureau/LO52/hikey/aarch64-linux-android-4.9/bin/aarch64-linux-android-gcc -E -Wp,-MD,arch/arm64/boot/dts/apm/.apm-mustang.dtb.d.pre.tmp -nostdinc -I./arch/arm64/boot/dts -I./arch/arm64/boot/dts/include -I./drivers/of/testcase-data -I./include -undef -D__DTS__ -x assembler-with-cpp -o arch/arm64/boot/dts/apm/.apm-mustang.dtb.dts.tmp arch/arm64/boot/dts/apm/apm-mustang.dts ; ./scripts/dtc/dtc -O dtb -o arch/arm64/boot/dts/apm/apm-mustang.dtb -b 0 -i arch/arm64/boot/dts/apm/  -d arch/arm64/boot/dts/apm/.apm-mustang.dtb.d.dtc.tmp arch/arm64/boot/dts/apm/.apm-mustang.dtb.dts.tmp ; cat arch/arm64/boot/dts/apm/.apm-mustang.dtb.d.pre.tmp arch/arm64/boot/dts/apm/.apm-mustang.dtb.d.dtc.tmp > arch/arm64/boot/dts/apm/.apm-mustang.dtb.d

source_arch/arm64/boot/dts/apm/apm-mustang.dtb := arch/arm64/boot/dts/apm/apm-mustang.dts

deps_arch/arm64/boot/dts/apm/apm-mustang.dtb := \
  arch/arm64/boot/dts/apm/apm-storm.dtsi \

arch/arm64/boot/dts/apm/apm-mustang.dtb: $(deps_arch/arm64/boot/dts/apm/apm-mustang.dtb)

$(deps_arch/arm64/boot/dts/apm/apm-mustang.dtb):
