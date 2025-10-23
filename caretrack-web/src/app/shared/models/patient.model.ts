export interface PatientDto {
  id: string;
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  gender: 'MALE' | 'FEMALE' | 'OTHER';
  phoneNumber?: string;
  email?: string;
  address?: string;
  primaryPhysicianId?: string;
}

export interface PatientListResponse {
  data: PatientDto[];
  total: number;
}
