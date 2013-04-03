##############################################################################
CONFIG = Debug
#CONFIG = Release

##############################################################################
.PHONY: all directory clean size

STACK_PATH = ../../..
APP_PATH = ..

CC = avr-gcc
OBJCOPY = avr-objcopy
SIZE = avr-size

CFLAGS += -W -Wall --std=gnu99 -Os
CFLAGS += -fdata-sections -ffunction-sections -fpack-struct -fshort-enums
CFLAGS += -funsigned-char -funsigned-bitfields
CFLAGS += -mmcu=atmega128rfa1
CFLAGS += -MD -MP -MT $(CONFIG)/$(*F).o -MF $(CONFIG)/$(@F).d

PRINTF_LIB = $(PRINTF_LIB_FLOAT)

ifeq ($(CONFIG), Debug)
  CFLAGS += -g
endif

LDFLAGS += -Wl,--gc-sections
LDFLAGS += -mmcu=atmega128rfa1


INCLUDES += \
  -I$(STACK_PATH)/hal/atmega128rfa1/inc \
  -I$(STACK_PATH)/phy/atmega128rfa1/inc \
  -I$(STACK_PATH)/nwk/inc \
  -I$(STACK_PATH)/sys/inc \
  -I$(APP_PATH)

SRCS += \
  $(STACK_PATH)/hal/atmega128rfa1/src/hal.c \
  $(STACK_PATH)/hal/atmega128rfa1/src/halTimer.c \
  $(STACK_PATH)/phy/atmega128rfa1/src/phy.c \
  $(STACK_PATH)/hal/atmega128rfa1/src/halSleep.c \
  $(STACK_PATH)/hal/atmega128rfa1/src/halUart.c \
  $(STACK_PATH)/hal/atmega128rfa1/src/halTemperature.c \
  $(STACK_PATH)/nwk/src/nwk.c \
  $(STACK_PATH)/nwk/src/nwkDataReq.c \
  $(STACK_PATH)/nwk/src/nwkSecurity.c \
  $(STACK_PATH)/nwk/src/nwkFrame.c \
  $(STACK_PATH)/nwk/src/nwkRoute.c \
  $(STACK_PATH)/nwk/src/nwkRx.c \
  $(STACK_PATH)/nwk/src/nwkTx.c \
  $(STACK_PATH)/sys/src/sys.c \
  $(STACK_PATH)/sys/src/sysTimer.c \
  $(STACK_PATH)/sys/src/sysEncrypt.c \
  $(APP_PATH)/CheckTemp.c

DEFINES += \
  -DPHY_ATMEGA128RFA1 \
  -DHAL_ATMEGA128RFA1 \
  -DPLATFORM_RCB128RFA1 \
  -DF_CPU=16000000

CFLAGS += $(INCLUDES) $(DEFINES)

OBJS = $(addprefix $(CONFIG)/, $(notdir %/$(subst .c,.o, $(SRCS))))

all: directory $(CONFIG)/CheckTemp.elf $(CONFIG)/CheckTemp.hex $(CONFIG)/CheckTemp.bin size

$(CONFIG)/CheckTemp.elf: $(OBJS)
	@echo LD $@
	@$(CC) $(LDFLAGS) $(OBJS) $(LIBS) -o $@

$(CONFIG)/CheckTemp.hex: $(CONFIG)/CheckTemp.elf
	@echo OBJCOPY $@
	@$(OBJCOPY) -O ihex -R .eeprom $^ $@

$(CONFIG)/CheckTemp.bin: $(CONFIG)/CheckTemp.elf
	@echo OBJCOPY $@
	@$(OBJCOPY) -O binary -R .eeprom $^ $@

%.o:
	@echo CC $@
	@$(CC) $(CFLAGS) $(filter %$(subst .o,.c,$(notdir $@)), $(SRCS)) -c -o $@

directory:
	@mkdir -p $(CONFIG)

size: $(CONFIG)/CheckTemp.elf
	@echo size:
	@$(SIZE) -t $^

clean:
	@echo clean
	@-rm -rf $(CONFIG)

upload:
	@echo upload
	/usr/local/avr/bin/avrdude -C /usr/local/avr/etc/avrdude.conf -pm128rfa1 -carduino -P/dev/tty.usbmodemfa131 -b57600 -D -v -v -Uflash:w:$(CONFIG)/CheckTemp.hex:i

-include $(wildcard $(CONFIG)/*.d)
