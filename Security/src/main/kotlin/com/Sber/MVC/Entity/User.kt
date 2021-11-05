package com.Sber.MVC.Entity

import javax.persistence.*

@Table(name = "usr")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
    private val username: String? = null
    private val password: String? = null
    //private val active = false

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    private val roles: Set<Role>? = null
}