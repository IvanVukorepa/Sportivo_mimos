using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class UserRepository
    {
        public ICollection<User> GetAll(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var users = context.Users.ToList();
                    return users;
                }
            }
            catch { return null; }
        }

        public User Get(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var user = context.Users.FirstOrDefault(c => c.UserId == id);
                    return user;
                }
            }
            catch { return null; }
        }

        public bool Add(User user)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Users.Add(user);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(User user, User updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    user.FirstName = updated.FirstName;
                    user.LastName = updated.LastName;
                    user.PhoneNumber = updated.PhoneNumber;
                    user.Password = updated.Password;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(User user)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Users.Remove(user);
                    return true;
                }
            }
            catch { return false; }
        }
    }
}
