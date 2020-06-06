package com.higherkindpud.rettuce.domain.repository

import com.higherkindpud.rettuce.domain.entity.{Report, Sale}
import com.higherkindpud.rettuce.domain.repository.SaleRepository.CreateRequest

trait SaleRepository[F[_]] {

  def findByName(name: String): F[Option[Sale]]

  def fetchAll: F[List[Sale]]

  def update(sale: Sale): F[Unit]

  def create(createRequest: CreateRequest): F[Unit]

  def bulkInsert(reports: List[Report]): F[Unit]
}

object SaleRepository {
  case class CreateRequest(vegetableName: Int, quantity: Int, amount: Int)
}
