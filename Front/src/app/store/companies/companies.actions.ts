import {PaginationParams} from '../../models/pagination.model';

export class LoadCompanies {
  static readonly type = '[Companies] Load Companies';
  constructor(public params: PaginationParams) {}
}

export class LoadCompanyVehicles {
  static readonly type = '[Companies] Load Company Vehicles';
  constructor(public companyId: string) {}
}

// export class LoadAvailableVehicles {
//   static readonly type = '[Companies] Load Available Vehicles';
// }

export class RegisterToCompany {
  static readonly type = '[Companies] Register To Company';
  constructor(public companyId: string) {}
}

export class UnregisterFromCompany {
  static readonly type = '[Companies] Unregister From Company';
  constructor(public companyId: string) {}
}
