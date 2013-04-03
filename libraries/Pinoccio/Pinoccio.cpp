#include <Arduino.h>
#include <Pinoccio.h>

#include "atmega128rfa1.h"
#include "utility/phy.h"
#include "utility/hal.h"
#include "utility/sys.h"
#include "utility/nwk.h"
#include "utility/sysTimer.h"
#include "utility/halSleep.h"
#include "utility/halTemperature.h"
#include "utility/halRgbLed.h"
#include "utility/webGainspan.h"
#include "utility/webWifi.h"
#include "utility/webWifiServer.h"
#include "utility/webWifiClient.h"
#include "utility/mqttClient.h"

PinoccioClass Pinoccio;
PinoccioConfig config;

// get us back our clean stream output: http://playground.arduino.cc/Main/StreamingOutput
template<class T> inline Print &operator <<(Print &obj, T arg) { obj.print(arg); return obj; }

PinoccioClass::PinoccioClass() {
  RgbLed.turnOff();

  pinMode(CHG_STATUS, INPUT);
  digitalWrite(CHG_STATUS, HIGH); // enable internal pull-up

  pinMode(BATT_CHECK, OUTPUT);
  digitalWrite(BATT_CHECK, LOW);

  pinMode(VCC_ENABLE, OUTPUT);
  digitalWrite(VCC_ENABLE, HIGH);
  
  config.channel               = 0;
  config.address               = 1;
  config.channel               = 0x1a;  // channel 26, at the end of the 2.4GHz spectrum
  config.panID                 = 0x4567;
  config.securityKey           = "TestSecurityKey0";
  config.enableRouting         = false;
  config.enableSecurity        = false;
  config.enableEnergyDetection = false;
}

PinoccioClass::~PinoccioClass() { }

void PinoccioClass::alive() {
  Serial.println("Hello, I'm alive");
}

void PinoccioClass::init() {
  Serial.begin(115200);

  SYS_Init();
  // TODO PHY_TX_PWR_REG(TX_PWR_3_2DBM);
  HAL_MeasureAdcOffset();
  
  if (config.enableSecurity == true) {
    Serial.println("Enabling security");
    NWK_SetSecurityKey((uint8_t *)config.securityKey);
  }
}

void PinoccioClass::initMesh(uint16_t address, uint16_t panID, uint16_t channel) {
  NWK_SetAddr(address);
  NWK_SetPanId(panID);
  PHY_SetChannel(channel);
  PHY_SetRxState(true);
}

void PinoccioClass::loop() {
  SYS_TaskHandler();
}

float PinoccioClass::getTemperature() {
  return HAL_MeasureTemperature();
}

bool PinoccioClass::isBatteryCharging() {
  return (digitalRead(CHG_STATUS) == LOW);
}

float PinoccioClass::getBatteryVoltage() {
  pinMode(A7, INPUT);
  digitalWrite(A7, LOW);
  digitalWrite(BATT_CHECK, HIGH);
  int read = analogRead(A7);
  digitalWrite(BATT_CHECK, LOW);
  digitalWrite(A7, HIGH);
  return read;
}

void PinoccioClass::enableShieldVcc() {
  digitalWrite(VCC_ENABLE, HIGH);
}

void PinoccioClass::disableShieldVcc() {
  digitalWrite(VCC_ENABLE, LOW);
}

uint16_t PinoccioClass::getRandomNumber() {
  uint16_t prevRand = randomNumber;
  PHY_RandomReq();
  while (randomNumber == prevRand) {}
  return randomNumber;
}

void PinoccioClass::setRandomNumber(uint16_t number) {
  randomNumber = number;
}

bool PinoccioClass::publish(char* topic, char* payload, int size) {

}

bool PinoccioClass::subscribe(char* topic, bool (*handler)(NWK_DataInd_t *msg)) {

}

bool PinoccioClass::sendMessage(NWK_DataReq_t *msg) {
  NWK_DataReq(msg);
}

bool PinoccioClass::listenForMessage(uint8_t id, bool (*handler)(NWK_DataInd_t *msg)) {
  NWK_OpenEndpoint(id, handler);
}

void PHY_RandomConf(uint16_t rnd) {
  Pinoccio.setRandomNumber(rnd);
}
