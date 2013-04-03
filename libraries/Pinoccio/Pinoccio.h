#ifndef LIB_PINOCCIO_H_
#define LIB_PINOCCIO_H_

#define PINOCCIO_DEBUG

#ifdef PINOCCIO_DEBUG
#  define D(x) x
#else
#  define D(x)
#endif

#define PHY_ENABLE_RANDOM_NUMBER_GENERATOR
#define NWK_ENABLE_SECURITY
#define NWK_ENABLE_ROUTING

#define SYS_SECURITY_MODE                   0
#define NWK_BUFFERS_AMOUNT                  3
#define NWK_MAX_ENDPOINTS_AMOUNT            3
#define NWK_DUPLICATE_REJECTION_TABLE_SIZE  10
#define NWK_DUPLICATE_REJECTION_TTL         3000 // ms
#define NWK_ROUTE_TABLE_SIZE                100
#define NWK_ROUTE_DEFAULT_SCORE             3
#define NWK_ACK_WAIT_TIME                   800 // ms

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

// typedef struct NWK_DataReq_t sendMessage;
// typedef struct NWK_DataInd_t receiveMessage;

typedef struct PinoccioConfig_ {
  uint16_t  address;
  uint16_t  channel;
  uint16_t  panID;
  char*     securityKey;
  uint8_t   enableRouting;
  uint8_t   enableSecurity;
  uint8_t   enableEnergyDetection;
} PinoccioConfig;

extern PinoccioConfig config;

class PinoccioClass {

  public:
    PinoccioClass();
    ~PinoccioClass();

    void alive();
    void init();
    void initMesh(uint16_t address, uint16_t panID, uint16_t channel);
    
    void loop();

    float getTemperature();

    bool isBatteryCharging();
    float getBatteryVoltage();

    void enableShieldVcc();
    void disableShieldVcc();

    uint16_t getRandomNumber();
    void setRandomNumber(uint16_t number);

    bool publish(char* topic, char* payload, int size);
    bool subscribe(char*, bool (*handler)(NWK_DataInd_t *msg));

    bool sendMessage(NWK_DataReq_t *msg);
    bool listenForMessage(uint8_t id, bool (*handler)(NWK_DataInd_t *msg));

  protected:
    uint16_t randomNumber;
};

extern PinoccioClass Pinoccio;

#endif