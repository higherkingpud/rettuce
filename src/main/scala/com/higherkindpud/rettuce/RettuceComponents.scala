package com.higherkindpud.rettuce

import java.util.concurrent.{ExecutorService, Executors}

import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import play.api.mvc.ControllerComponents
import com.higherkindpud.rettuce.config.RettuceConfig
import com.higherkindpud.rettuce.controller.VegetableController
import com.higherkindpud.rettuce.domain.repository.VegetableRepository
import com.higherkindpud.rettuce.domain.service.VegetableService
import com.higherkindpud.rettuce.infra.db.MySQLComponents
import com.higherkindpud.rettuce.infra.redis.VegetableRepositoryOnRedis
import com.higherkindpud.rettuce.infra.redis.common.DefaultRedisCache
import redis.clients.jedis.JedisPool

import scala.concurrent.ExecutionContext

trait RettuceComponents extends MySQLComponents {
  lazy val config: RettuceConfig =
    ConfigSource.fromConfig(ConfigFactory.load()).loadOrThrow[RettuceConfig]
  lazy val mySQLConfig = config.mysql
  //domain
  lazy val vegetableService = wire[VegetableService]

  //controller
  def controllerComponents: ControllerComponents
  lazy val vegetableController: VegetableController = wire[VegetableController]

  //repository
  lazy val pool: JedisPool   = new JedisPool(config.redis.host, config.redis.port)
  lazy val defaultRedisCache = wire[DefaultRedisCache]

  lazy val vegetableRepository: VegetableRepository = wire[VegetableRepositoryOnRedis]
}
