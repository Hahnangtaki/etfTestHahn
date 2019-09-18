import { IBukuMySuffix } from 'app/shared/model/buku-my-suffix.model';

export interface IMahasiswaMySuffix {
  id?: string;
  nim?: string;
  nama?: string;
  alamat?: string;
  bukus?: IBukuMySuffix[];
}

export const defaultValue: Readonly<IMahasiswaMySuffix> = {};
