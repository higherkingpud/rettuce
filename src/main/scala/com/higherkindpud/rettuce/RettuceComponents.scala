package com.higherkindpud.rettuce

import com.softwaremill.macwire.wire
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import play.api.mvc.ControllerComponents

import com.higherkindpud.rettuce.config.RettuceConfig
import com.higherkindpud.rettuce.controller.VegetableController
import com.higherkindpud.rettuce.domain.repository.VegetableRepository
import com.higherkindpud.rettuce.domain.service.VegetableService
import com.higherkindpud.rettuce.infra.redis.VegetableRepositoryOnRedis
import com.higherkindpud.rettuce.infra.redis.common.DefaultRedisCache

import redis.clients.jedis.JedisPool

trait RettuceComponents {
  lazy val config: RettuceConfig =
    ConfigSource.fromConfig(ConfigFactory.load()).loadOrThrow[RettuceConfig]

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
