/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import AttachService from './attach.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Attach } from '@/shared/model/attach.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Attach Service', () => {
    let service: AttachService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AttachService();
      currentDate = new Date();
      elemDefault = new Attach(123, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Attach', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Attach', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Attach', async () => {
        const returnedFromService = Object.assign(
          {
            ord: 1,
            name: 'BBBBBB',
            origName: 'BBBBBB',
            ext: 'BBBBBB',
            contentType: 'BBBBBB',
            path: 'BBBBBB',
            fileSize: 1,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdBy: 1,
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedBy: 1,
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Attach', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Attach', async () => {
        const patchObject = Object.assign(
          {
            ord: 1,
            origName: 'BBBBBB',
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new Attach(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Attach', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Attach', async () => {
        const returnedFromService = Object.assign(
          {
            ord: 1,
            name: 'BBBBBB',
            origName: 'BBBBBB',
            ext: 'BBBBBB',
            contentType: 'BBBBBB',
            path: 'BBBBBB',
            fileSize: 1,
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdBy: 1,
            modifiedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            modifiedBy: 1,
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Attach', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Attach', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Attach', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
