#include <Pinoccio.h>

void setup() { 
  Pinoccio.init();
}

void loop() {
  Pinoccio.loop();
  
  RgbLed.blinkRed();
  RgbLed.blinkGreen();
  RgbLed.blinkBlue();
}