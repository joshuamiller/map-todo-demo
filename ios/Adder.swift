//
//  Adder.swift
//  MapTodoDemo
//
//  Created by Joshua Miller on 10/5/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

import Foundation

@objc(Adder)
class Adder : NSObject {
  @objc func add(_ a:NSNumber, b:NSNumber, callback: @escaping RCTResponseSenderBlock) -> Void {
    callback([NSNull(), a.floatValue + b.floatValue])
  }
}
