#import "VinidpaySdkPlugin.h"
#if __has_include(<vinidpay_sdk/vinidpay_sdk-Swift.h>)
#import <vinidpay_sdk/vinidpay_sdk-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "vinidpay_sdk-Swift.h"
#endif

@implementation VinidpaySdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftVinidpaySdkPlugin registerWithRegistrar:registrar];
}
@end
