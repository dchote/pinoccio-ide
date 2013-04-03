#include <Pinoccio.h>

void setup() {
  Pinoccio.init();
}

void loop() {
  Pinoccio.loop();
  
  if (Pinoccio.isBatteryPresent()) {
    Serial.println("A battery is present.");
  } else {
    Serial.println("No battery is present.");
  }
  delay(3000);
}
