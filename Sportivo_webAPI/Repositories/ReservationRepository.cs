using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class ReservationRepository
    {
        public ICollection<Reservation> GetAllForUser(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservations = context.Reservations.Where(r => r.UserId == id).ToList();
                    return reservations;
                }
            }
            catch { return null; }
        }

        public Reservation Get(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservation = context.Reservations.FirstOrDefault(c => c.ReservationId == id);
                    return reservation;
                }
            }
            catch { return null; }
        }

        public bool Add(Reservation reservation)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Reservations.Add(reservation);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(Reservation reservation, Reservation updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    reservation.DateTime = updated.DateTime;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(Reservation reservation)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Reservations.Remove(reservation);
                    return true;
                }
            }
            catch { return false; }
        }
    }
}
