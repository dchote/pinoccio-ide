#ifndef _CONFIG_H_
#define _CONFIG_H_

/*****************************************************************************/
PinoccioConfig config;

config.address = 1;

#define P_ADDRESS                = 1;
#define P_CHANNEL                = 0x1a;  // channel 26, at the end of the 2.4GHz spectrum
#define P_PAN_ID                 = 0x4567;
#define P_ENDPOINT               = 1;
#define P_SECURITY_KEY           = "TestSecurityKey1";
#define P_ENABLE_ROUTING         = true;
#define P_ENABLE_SECURITY        = true;
#define P_ENABLE_ENERGY_DETETION = false;

/*****************************************************************************/

#endif // _CONFIG_H_