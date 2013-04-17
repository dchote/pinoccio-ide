#include "config.h"
#include <Pinoccio.h>

void setup() {
  Pinoccio.init();
  Serial.begin(115200);
}

void loop() {
  Pinoccio.taskHandler();

  Serial.print("Temp: ");
  Serial.println(Pinoccio.getTemperature());
  delay(1000);
}