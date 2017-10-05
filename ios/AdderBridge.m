//
//  AdderBridge.m
//  MapTodoDemo
//
//  Created by Joshua Miller on 10/5/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(Adder, NSObject)
RCT_EXTERN_METHOD(add:(nonnull NSNumber *) a b:(nonnull NSNumber *) b callback:(RCTResponseSenderBlock) callback)
@end
