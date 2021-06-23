import 'dart:async';

import 'package:flutter/services.dart';

class VinidpaySdk {
  static const MethodChannel _channel = const MethodChannel('vinidpay_sdk');

  static Future<VinidpaySdkStatus> proceedPayment(
    String id,
    String sign,
    bool sandboxMode,
  ) async {
    final String? _status =
        await _channel.invokeMethod('proceedPayment', <String, dynamic>{
      'id': id,
      'sign': sign,
      'sandboxMode': sandboxMode,
    });
    switch (_status) {
      case 'payment successful!':
        return VinidpaySdkStatus.SUCCESS;
      case 'user aborted payment':
        return VinidpaySdkStatus.ABORT;
      case 'payment failed':
        return VinidpaySdkStatus.FAIL;
      default:
        return VinidpaySdkStatus.UNKNOW;
    }
  }

  static Future<bool?> isVinIdAppInstalled(bool sandboxMode) async {
    final bool? _status =
        await _channel.invokeMethod('isVinIdAppInstalled', <String, dynamic>{
      'sandboxMode': sandboxMode,
    });
    return _status;
  }

  static openVinIDInstallPage(bool sandboxMode) async {
    _channel.invokeMethod('openVinIDInstallPage', <String, dynamic>{
      'sandboxMode': sandboxMode,
    });
  }
}

/// The status after a VinidpaySdk flow has completed.
enum VinidpaySdkStatus { SUCCESS, ABORT, FAIL, UNKNOW }
