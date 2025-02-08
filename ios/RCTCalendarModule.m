//
//  RCTCalendarModule.m
//  Test0_71
//
//  Created by Sunil Jaunwal on 20/01/25.
//

#import "RCTCalendarModule.h"
#import <React/RCTLog.h> // Correct import for RCTLog

@implementation RCTCalendarModule

// Exported method to create a calendar event
RCT_EXPORT_METHOD(createCalendarEvent:(NSString *)name location:(NSString *)location myCallback:(RCTResponseSenderBlock) callback)
{
  RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
  NSNumber *eventId = [NSNumber numberWithInt:123];
   callback(@[[NSNull null], eventId]);
}

// To export a module named RCTCalendarModule
RCT_EXPORT_MODULE(CalenderModule);

// Export the module name method (optional)

@end
