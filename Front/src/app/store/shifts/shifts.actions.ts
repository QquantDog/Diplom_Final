export class LoadCurrentShift {
  static readonly type = '[Shifts] Load Current Shift';
}

export class LoadShiftHistory {
  static readonly type = '[Shifts] Load Shift History';
  constructor(public limit: number = 5) {}
}

export class LoadAvailableCabs {
  static readonly type = '[Shifts] Load Available Cabs';
}

export class StartShift {
  static readonly type = '[Shifts] Start Shift';
  constructor(public cabId: string) {}
}

export class EndShift {
  static readonly type = '[Shifts] End Shift';
}
