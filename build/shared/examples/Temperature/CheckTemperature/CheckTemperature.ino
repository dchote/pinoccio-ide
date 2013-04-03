#include "config.h"
#include <Pinoccio.h>

void setup() {
  Pinoccio.init();
}

void loop() {
  Pinoccio.loop();

  Serial.print("Temp: ");
  Serial.println(Pinoccio.getTemperature());
  delay(3000);
}