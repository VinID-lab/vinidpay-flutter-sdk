# vinidpay_sdk_example

Demonstrates how to use the vinidpay_sdk plugin.

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://flutter.dev/docs/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://flutter.dev/docs/cookbook)

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.


## Example

````dart

import 'package:vinidpay_sdk/vinidpay_sdk.dart';

// Platform messages are asynchronous, so we initialize in an async method.
Future<void> proceedPayment(
  String orderId,
  String signature,
  bool sandboxMode,
) async {
  VinidpaySdkStatus _status;
  // Platform messages may fail, so we use a try/catch PlatformException.
  try {
    _status = await VinidpaySdk.proceedPayment(
      orderId,
      signature,
      sandboxMode,
    );
  } on PlatformException {
    _status = VinidpaySdkStatus.UNKNOW;
  }

  // If the widget was removed from the tree while the asynchronous platform
  // message was in flight, we want to discard the reply rather than calling
  // setState to update our non-existent appearance.
  if (!mounted) return;

  //Doing something
  switch (_status) {
    case VinidpaySdkStatus.SUCCESS:
  
    case VinidpaySdkStatus.ABORT:
  
    case VinidpaySdkStatus.FAIL:
  
    case VinidpaySdkStatus.UNKNOW:
  
    default:
  
  }

}

Future<void> isVinIdAppInstalled(bool sandboxMode) async {
  bool _status;
  _status = await VinidpaySdk.isVinIdAppInstalled(sandboxMode);
  print(_status);
}

openVinIDInstallPage(bool sandboxMode) {
  VinidpaySdk.openVinIDInstallPage(sandboxMode);
}
    
````